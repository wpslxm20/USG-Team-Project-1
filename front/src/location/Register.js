// Register.js

import React, { useState } from 'react';
import axios from 'axios';

function Register() {
  const [regName, setRegName] = useState('');
  const [regComment, setRegComment] = useState('');
  const [regPhone, setRegPhone] = useState('');
  const [regAddr, setRegAddr] = useState('');
  const [regType, setRegType] = useState('');
  const [regImg, setRegImg] = useState(null);

  const handleReg = async () => {
    try {
      const formData = new FormData();
      formData.append('name', regName);
      formData.append('comment', regComment);
      formData.append('phone', regPhone);
      formData.append('addr', regAddr);
      formData.append('type', regType);
      formData.append('memberId', 1);
      formData.append('imgFile', regImg);

      console.log('전송 데이터 :', Object.fromEntries(formData));
      
      const response = await axios.post('http://localhost:8081/api/location/reg', formData, {
        withCredentials: false, // credentials 설정
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });

      console.log('등록 성공:', response.data);
    } catch (error) {
      console.error('에러 발생:', error.message);
    }
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    setRegImg(file);
  };

  return (
    <div>
      <h2>매장(업소)등록</h2>
      <form>
        <label>
          매장 이름:
          <input type="text" style={{width: '500px'}} value={regName} onChange={(e) => setRegName(e.target.value)} />
        </label>
        <br />
        <label>
          매장 코멘트:
          <input type="text" style={{width: '500px'}} value={regComment} onChange={(e) => setRegComment(e.target.value)} />
        </label>
        <br />
        <label>
          매장 전화번호:
          <input type="text" style={{width: '500px'}} value={regPhone} onChange={(e) => setRegPhone(e.target.value)} />
        </label>
        <br />
        <label>
          매장 주소:
          <input type="text" style={{width: '500px'}} value={regAddr} onChange={(e) => setRegAddr(e.target.value)} />
        </label>
        <br />
        <label>
          매장 타입:
          <input type="text" style={{width: '500px'}} placeholder='SIGHTS, CAFE, RESTAURANT, PLAYGROUND' value={regType} onChange={(e) => setRegType(e.target.value)} />
        </label>
        <br />
        <label>
          매장 이미지:
          <input type="file" onChange={handleImageChange} />
        </label>
        <br />
        <button type="button" onClick={handleReg}>
          등록
        </button>
      </form>
    </div>
  );
}

export default Register;
