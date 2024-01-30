// RegisterUpdate.js

import React, { useState } from 'react';
import axios from 'axios';

function RegisterUpdate() {
  const [updateId, setUpdateId] = useState('');
  const [updateName, setUpdateName] = useState('');
  const [updateComment, setUpdateComment] = useState('');
  const [updatePhone, setUpdatePhone] = useState('');
  const [updateAddr, setUpdateAddr] = useState('');
  const [updateType, setUpdateType] = useState('');
  const [updateImg, setUpdateImg] = useState(null);

  const handleUpdate = async () => {
    try {
      const formData = new FormData();
      formData.append('id', updateId); // 이거는 페이지 url에 나타나서 가져와야 함 실제로는
      formData.append('name', updateName);
      formData.append('comment', updateComment);
      formData.append('phone', updatePhone);
      formData.append('addr', updateAddr);
      formData.append('type', updateType);
      formData.append('memberId', 1); // 이거는 페이지 url에 나타나서 가져와야 함 실제로는
      formData.append('imgFile', updateImg);

      console.log('전송 데이터 :', Object.fromEntries(formData));
      
      const response = await axios.put(`http://localhost:8081/api/location/update/${updateId}`, formData, {
        withCredentials: false, // credentials 설정
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });

      console.log('수정 성공:', response.data);
    } catch (error) {
      console.error('에러 발생:', error.message);
    }
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    setUpdateImg(file);
  };

  return (
    <div>
      <h2>매장(업소)수정</h2>
      <form>
      <label>
          수정할 ID:
          <input type="number" style={{width: '500px'}} value={updateId} onChange={(e) => setUpdateId(e.target.value)} />
        </label>
        <br />
        <label>
          매장 이름:
          <input type="text" style={{width: '500px'}} value={updateName} onChange={(e) => setUpdateName(e.target.value)} />
        </label>
        <br />
        <label>
          매장 코멘트:
          <input type="text" style={{width: '500px'}} value={updateComment} onChange={(e) => setUpdateComment(e.target.value)} />
        </label>
        <br />
        <label>
          매장 전화번호:
          <input type="text" style={{width: '500px'}} value={updatePhone} onChange={(e) => setUpdatePhone(e.target.value)} />
        </label>
        <br />
        <label>
          매장 주소:
          <input type="text" style={{width: '500px'}} value={updateAddr} onChange={(e) => setUpdateAddr(e.target.value)} />
        </label>
        <br />
        <label>
          매장 타입:
          <input type="text" style={{width: '500px'}} placeholder='SIGHTS, CAFE, RESTAURANT, PLAYGROUND' value={updateType} onChange={(e) => setUpdateType(e.target.value)} />
        </label>
        <br />
        <label>
          매장 이미지:
          <input type="file" onChange={handleImageChange} />
        </label>
        <br />
        <button type="button" onClick={handleUpdate}>
          수정
        </button>
      </form>
    </div>
  );
}

export default RegisterUpdate;
