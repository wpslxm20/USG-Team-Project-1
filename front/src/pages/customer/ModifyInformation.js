// src/pages/ModifyInformation.js
import React, { useState, useEffect } from 'react';
import * as Yup from 'yup';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { Container, Grid, TextField, Typography } from "@mui/material";
import Button from '@mui/material/Button';
import Paper from "@mui/material/Paper";
import { RadioGroup, FormControlLabel, Radio } from '@mui/material';
import { useDropzone } from 'react-dropzone';

// 입력 데이터의 유효성 검사
const validationSchema = Yup.object({
  nickname: Yup.string().required('닉네임을 입력하세요'),
  email: Yup.string().email('올바른 이메일 주소를 입력하세요').required('이메일을 입력하세요'),
  password: Yup.string().min(8, '비밀번호는 최소 8자 이상이어야 합니다').required('비밀번호를 입력하세요'),
  passwordcheck: Yup.string()
    .oneOf([Yup.ref('password'), null], '비밀번호가 일치하지 않습니다')
    .required('비밀번호를 다시 입력하세요'),
  birth: Yup.date().required('생년월일을 입력하세요'),
  gender: Yup.string().required('성별을 선택하세요')
});

const ModifyInformation = () => {
  const [values, setValues] = useState({
    email: '',
    password: '',
    passwordcheck: '',
    nickname: '',
    birth: '',
    gender: '',
    profileImage: null
  });
  const [errors, setErrors] = useState({});

  // 현재 저장되어 있는(수정 전) 값 불러오기
  useEffect(() => {
    // 데이터 가져오기
    axios.get('https://jsonplaceholder.typicode.com/users')
      .then(response => {
        const userData = response.data;

        // 가져온 데이터로 상태 업데이트
        setValues(userData);
      })
      .catch(error => {
        console.error('사용자 데이터 가져오기 실패:', error);
      });
  }, []); // 처음 로드될 때만 

  const uploadImage = async (file) => {
    try {
      // 이미지를 formData에 저장
      const formData = new FormData();
      formData.append('image', file);

      // 서버에 이미지 업로드 요청
      const response = await axios.post('https://jsonplaceholder.typicode.com/users', formData);
      // 서버에서 반환한 이미지 URL을 저장
      const imageUrl = response.data.imageUrl;

      // 업로드 성공 시 상태 업데이트와 화면에 이미지 출력
      setValues({
        ...values,
        profileImage: imageUrl,
      });
    } catch (error) {
      console.error('이미지 업로드 실패:', error);
    }
  };

  const { getRootProps, getInputProps } = useDropzone({
    accept: 'image/*',
    onDrop: (acceptedFiles) => {
      // 이미지를 업로드하고, 업로드 성공 시 state에 저장
      const file = acceptedFiles[0];
      uploadImage(file);
    }
  });

  // 입력 요소의 변경 이벤트가 발생했을 때 호출되는 함수
  const handleChange = (e) => {
    // name : 입력 필드의 이름, value : 입력 필드의 값 -> 값 추출
    const { name, value } = e.target;
    setErrors({
      ...errors, [name]: ''
    });
    setValues({
      ...values,
      [name]: value,
    });
  };
  
  // 폼을 제출할 때 실행되는 함수
  const handleSubmit = async (e) => {
    // 폼의 기본 제출 동작 방지
    e.preventDefault();

    // 입력된 값 유효성 검사 -> valid : 유효성 검사에 통과된 값들
    await validationSchema.validate(values, { abortEarly: false }).then((valid) => {
      // API로 수정 요청 
      axios.post('https://jsonplaceholder.typicode.com/users', values)
        .then(response => {
          console.log('정보 수정 성공:', response.data);
          // 성공 시 팝업 표시
           window.alert('정보 수정이 완료되었습니다.');
        })
        .catch(error => {
          console.error('정보 수정 실패:', error);
        });
    }).catch((err) => {
      console.log('err : ', err)
      const errorMessages = {};
      err.inner.forEach((error) => {
        errorMessages[error.path] = error.message;
      });
      setErrors(errorMessages);
    });
  };

 
  return (
    <Container component="main" style={{ padding: '5%', width: '80%'}} >
      <Paper
        elevation={3}
        sx={{
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          padding: 3,
          border: '2px solid #99D98C',
          borderRadius: '10px'
        }}
      >
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
          <Typography component="h1" variant="h5" mb={2} style={{marginBottom: '20px'}}>
            내 정보 수정
          </Typography>
          {/* 파일 드랍 영역 생성 */}
          <div {...getRootProps()} style={{ cursor: 'pointer' }}>
            {/* 파일 업로드 처리 */}
            <input {...getInputProps()} />

            {/* values.profileImage에 값이 있는지 확인 ? true이면 저장되어 있는 사용자의 프로필과 업로드 버튼 : false이면 기본 프로필과 업로드 버튼 */}
            {values.profileImage ? (
              <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                <img src={values.profileImage} style={{ width: '300px', height: '300px', marginBottom: '10%' }} alt="프로필 이미지" />
                <Button variant="contained" component="div" style={{marginBottom: '5%'}}>
                    프로필 이미지 업로드
                </Button>
              </div>
            ) : (
              <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                <img src={process.env.PUBLIC_URL + '/profile_image.jpeg'} style={{ width: '300px', height: '300px', marginBottom: '10%' }} alt="프로필 이미지" />
                <Button variant="contained" component="div" style={{marginBottom: '5%', color: '#000', backgroundColor: '#fff', border: '1px solid #000'}} >
                  프로필 이미지 업로드
                </Button>
              </div>
              )}
          </div>
        </div>
        
        <form
          noValidate  // 기본 유효성 검사 비활성화
          onSubmit={handleSubmit}
          sx={{ width: '100%', marginTop: 3 }}
        >
            <Grid container spacing={2} alignItems="center">
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  id="email"
                  label="이메일"
                  name="email"
                  autoComplete="email"
                  value={values.email}
                  onChange={handleChange}
                  error={Boolean(errors.email)}
                  helperText={errors.email}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="password"
                  label="비밀번호"
                  type="password"
                  id="password"
                  autoComplete="new-password"
                  value={values.password}
                  onChange={handleChange}
                  error={Boolean(errors.password)}
                  helperText={errors.password}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="passwordcheck"
                  label="비밀번호 확인"
                  type="password"
                  id="passwordcheck"
                  autoComplete="new-password"
                  value={values.passwordcheck}
                  onChange={handleChange}
                  error={Boolean(errors.passwordcheck)}
                  helperText={errors.passwordcheck}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  id="nickname"
                  label="닉네임"
                  name="nickname"
                  value={values.nickname}
                  onChange={handleChange}
                  error={Boolean(errors.nickname)}
                  helperText={errors.nickname}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  id="birth"
                  label="생년월일"
                  name="birth"
                  type="date"  
                  InputLabelProps={{
                    shrink: true,
                  }}
                  value={values.birth}
                  onChange={handleChange}
                  error={Boolean(errors.birth)}
                  helperText={errors.birth}
                />
              </Grid>

              <Grid item xs={12}>
                <RadioGroup
                  row
                  id="gender"
                  name="gender"
                  value={values.gender}
                  onChange={handleChange}
                >
                  <FormControlLabel
                    value="male"
                    control={<Radio />}
                    label="남성"
                  />
                  <FormControlLabel
                    value="female"
                    control={<Radio />}
                    label="여성"
                  />
                </RadioGroup>
                {errors.gender && (
                  <p style={{ color: 'red' }}>{errors.gender}</p>
                )}
              </Grid>
            </Grid>
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 1 }}
          >
            수정하기
          </Button>
        </form>
      </Paper>
    </Container>
  );
};

export default ModifyInformation;
