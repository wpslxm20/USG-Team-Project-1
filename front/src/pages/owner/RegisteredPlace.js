// src/pages/RegisteredPlace.js
import React, { useState, useEffect  } from 'react';
import { IoSearch } from "react-icons/io5";

const styles = {
  table: {
    width: '100%',
    borderCollapse: 'collapse',
  },
  tableHead: {
    backgroundColor: '#D1E8C4',
  },
  tableCell: {
    padding: '12px 16px',
  },
};

const RegisteredPlace = () => {
  // 입력된 장소
  const [placeName, setPlaceName] = useState('');
  // 선택된 업종
  const [category, setCategory] = useState('');
  // 목록
  const [registeredPlaces, setRegisteredPlaces] = useState([]);

  // 초기 데이터 가져오기
  useEffect(() => {
    const fetchInitialData = async () => {
      try {
        // 서버 엔드포인트 URL
        const apiUrl = 'https://jsonplaceholder.typicode.com/places';

        // 서버에서 초기 데이터 가져오기
        const response = await fetch(apiUrl);
        
        // 서버 응답 성공 여부 확인
        if (!response.ok) {
          throw new Error('서버 응답이 실패했습니다.');
        }

        // 서버에서 받아온 데이터 처리
        const initialData = await response.json();

        // 초기 데이터를 목록에 업데이트
        setRegisteredPlaces(initialData);
      } catch (error) {
        console.error('데이터 로딩 중 오류가 발생했습니다:', error.message);
      }
    };

    // 페이지가 처음 호출되었을 때 초기 데이터 로딩 실행
    fetchInitialData();
  }, []);

  const handleSearch = async () => {
    try {
      // 테스트용 데이터 --------------------------------------------------------------------------------------------------------
      const testSearchResults = [
        { id: 1, name: '장소 1', category: '카페', rating: 4.5 },
        { id: 2, name: '장소 2', category: '음식점', rating: 3.8 },
      ];
      // ---------------------------------------------------------------------------------------

      // 서버 엔드포인트 URL
      const apiUrl = 'https://jsonplaceholder.typicode.com/places';
  
      // 서버로 전송할 데이터
      const requestData = {
        placeName,
        category,
      };

      // 테스트용 데이터 사용 -------------------------------------------------------------------
      const searchResults = testSearchResults;
      // ---------------------------------------------------------------------------------------
  
      // ---------------------------------------------------------------------------------------
      // // Fetch API를 사용하여 서버에 데이터 전송
      // const response = await fetch(apiUrl, {
      //   method: 'POST', 
      //   headers: {
      //     //'Content-Type': 'application/json',
          
      //   },
      //   // 검색 결과 목록 전송
      //   body: JSON.stringify(requestData),
      // });
  
      // // 응답이 성공적인지 확인
      // if (!response.ok) {
      //   throw new Error('서버 응답이 실패했습니다.');
      // }
  
      // // 서버에서 받아온 데이터 처리
      // const searchResults = await response.json();
      // ---------------------------------------------------------------------------------------

      // 사용자가 입력한 장소 이름과 일치하는 항목 필터링 
      const filteredResults = searchResults.filter((place) => {
        return place.name.includes(placeName);
      });
  
      // 서버에서 받아온(처리된 데이터) 검색 결과 업데이트
      setRegisteredPlaces(searchResults);
    } catch (error) {
      console.error('검색 중 오류가 발생했습니다:', error.message);
    }
  };

  const handleEditReview = (placeId) => {
    // 특정 장소의 리뷰 수정 페이지로 이동하는 로직을 추가
    // 여기에서는 예시로 console.log를 사용하고, 실제로는 페이지 이동 등의 로직을 추가해야 합니다.
    console.log(`Edit review for place with ID ${placeId}`);
  };

  return (
    <div style={{ padding: '5%', width: '80%'}}>
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'flex-end', marginBottom: 0, marginLeft: '20%' }}>
        <p style={{ width: '10%', marginLeft: '20px' }}>업종 : </p>
        <select
          style={{ textAlign: 'center', border: '1px solid #333', width: '20%', marginRight: '20px', height: '30px' }}
          value={category}
          onChange={(e) => setCategory(e.target.value)}
        >
          <option value="">전체</option>
          <option value="카페">카페</option>
          <option value="음식점">음식점</option>
          <option value="명소">명소</option>
          <option value="놀거리">놀거리</option>
        </select>
        <input
          type="text"
          id="place_name_search"
          placeholder="장소 이름을 입력하세요."
          style={{ width: '40%', height: '30px' }}
          value={placeName}
          onChange={(e) => setPlaceName(e.target.value)}
        />
        <button style={{ width: '40px', height: '40px', marginLeft: '10px',backgroundColor: '#99D98C', border: '0'}} onClick={handleSearch}> <IoSearch size={20} /> </button>
        
      </div>
      <p style={{ width: '20%', textAlign: 'left' }}>내가 등록한 장소 ({registeredPlaces.length}개)</p>
      <div style={{ paddingTop: '5px' }}>
        <table style={styles.table}>
          <thead style={{ ...styles.tableHead }}>
            <tr>
              <th style={{...styles.tableCell, width: '10%'}}>목록</th>
              <th style={{...styles.tableCell, width: '20%'}}>장소</th>
              <th style={{...styles.tableCell, width: '30%'}}>내용</th>
              <th style={{...styles.tableCell, width: '20%'}}>평점</th>
              <th style={{...styles.tableCell, width: '20%', backgroundColor: '#fff', borderBottom: 'none'}}></th>
            </tr>
          </thead>
          <tbody>
            {registeredPlaces.map((place, index) => (
              <tr key={place.id} style={{ borderBottom: index === registeredPlaces.length - 1 ? '1px solid #D5E8CE' : 'none' }}>
                <td style={{...styles.tableCell, width: '10%'}}>{index+1}</td>
                <td style={{...styles.tableCell, width: '20%'}}>{place.name}</td>
                <td style={{...styles.tableCell, width: '30%'}}>{place.category}</td>
                <td style={{...styles.tableCell, width: '20%'}}>{place.rating}</td>
                <td style={{...styles.tableCell, width: '20%'}}>
                  <button
                    style={{ backgroundColor: '#D1E8C4', border: '0', cursor: 'pointer', padding: '10px', fontSize: '16px', borderBottom: 'none'}}
                    onClick={() => handleEditReview(place.id)}
                  >
                    수정하기
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default RegisteredPlace;
