import React, { useState } from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from '@mui/material';

const Main = () => {
    const [data, setDate] = useState( {
        name: '', // 이름
        score: '', // 평점
        comment: '', // 코멘트
        addr: '', // 가볍게 주소 4개 데이터 보여주기
    });

    const handleSubmit = (e) => { // 데이터 전송 (프론트 -> 백)
        e.preventDefault();

        try {
            const response = fetch('http://localhost:8080/myendpoint', {
                method: 'POST', // POST 요청 전송
                headers: {
                    'Content-Type': 'application/json', // JSON 형태 데이터 전송
                },
                body: JSON.stringify(data), // 데이터를 JSON 문자여로 변환
            });
        
            // 전송 실패
            if (!response.ok) {
                throw new Error('연결 오류');
            }

            // 성공적
            console.log('전송 성공');
        } catch (error) {
            // 요청 중 에러 발생
            console.error('Error sending data:', error.message);
        }
    };

    // 이 페이지는 DB에서 받아와야 하네..
    // 1. DB에서 데이터를 받아서 DTO 객체에 저장해서 전송
    // 2. 받아온 데이터를 통해 삽입
    return (
        <form onSubmit={handleSubmit}>
        <Table border='1'>
            <TableHead >
                <TableCell colSpan="4">명소</TableCell>
            </TableHead>
            <TableBody>
                <TableRow>
                    <TableCell>엥</TableCell>
                    <TableCell>엥</TableCell>
                    <TableCell>엥</TableCell>
                    <TableCell>엥</TableCell>
                </TableRow>
            </TableBody>
        </Table>
        </form>
    );
} 



export default Main;