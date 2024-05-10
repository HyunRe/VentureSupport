/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */
import React, { useEffect, useState } from 'react';
import { ActivityIndicator, FlatList, View, Text, TextInput, TouchableOpacity, StyleSheet, ScrollView, Image, Button } from 'react-native';
//import NaverLoginComponent from './logins/naver_login';
import { useNavigation } from '@react-navigation/native';

// 로그인 화면을 나타내는 컴포넌트입니다.
const LoginScreen = ({ navigation }) => {

  // 이메일과 비밀번호를 관리하기 위한 상태 변수들입니다.
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isLoading, setLoading] = useState(true);
  const [data, setData] = useState([]);

  // 로그인 시 발생하는 에러를 관리하기 위한 상태 변수입니다.
  const [loginError, setLoginError] = useState('');

  // 로그인 상태를 관리하는 상태 변수입니다.
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  // 로그인 버튼을 눌렀을 때 실행되는 함수입니다.
  const handleLogin = async () => {
    const url = 'http://223.194.157.56:8080/login';

    try {
      const response = await fetch(url, {
        method: 'POST',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
          credentials: "include",
        },
        body: JSON.stringify({ //body 내 정보를 JSON 문자열로 변환
          email: email,
          password: password,
        }),
      });

      if (!response.ok) { //이상 감지할 시
        throw new Error('Network response was not ok');
        JSON.parse(response)
      }

      {/*서버로부터 응답 데이터를 수신받는 함수: 서버는 문자열 형식으로 반환하기 때문에 데이터타입은 반드시 text로*/}
      const data = await response.text(); //response.text()는 반드시.
      
      console.log('Login response:', data);

      // 서버로부터 받은 응답을 처리합니다.
      if (data.success !== undefined) {
        console.log('Success response:', data);
        // 성공적인 응답 처리
      } else {
        setLoginError('이메일 또는 비밀번호가 올바르지 않습니다.');
      }
    } catch (error) {
      // 로그인 과정에서 발생한 에러를 처리합니다.
      console.error('Login error:', error);
      setLoginError('로그인 중 오류가 발생했습니다.');
    }
  };

  return (
    // 로그인 화면 전체를 감싸는 View입니다.
    <View style={styles.container}>
      {/* 로그인 화면 제목입니다. */}
      <Text style={styles.title}>로그인</Text>
      {/* 이메일 입력 필드입니다. */}
      <TextInput
        style={styles.input}
        value={email}
        onChangeText={setEmail}
        placeholder="Email"
        keyboardType="email-address"
        autoCapitalize="none"
      />
      {/* 비밀번호 입력 필드입니다. */}
      <TextInput
        style={styles.input}
        value={password}
        onChangeText={setPassword}
        placeholder="Password"
        autoCapitalize="none"
        secureTextEntry
      />
      {/* 로그인 버튼입니다. */}
      <Button title="로그인" onPress={handleLogin} />
      {/* 비밀번호 찾기 링크입니다. */}
      <Text style={styles.forgotPassword}>비밀번호를 잊어버리셨나요?</Text>
      {/* 회원가입 버튼입니다. */}
      <Button title="회원가입" onPress={() => {}} />
      {/* 네이버 로그인 버튼입니다. */}
      <Button title="네이버 로그인" onPress={() => {}} />
    </View>
  );
};

// 스타일 시트입니다.
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
  },
  title: {
    fontSize: 34,
    fontWeight: 'bold',
    marginBottom: 20,
  },
  input: {
    width: '100%',
    height: 50,
    marginBottom: 30,
    paddingHorizontal: 10,
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 5,
  },
  button: {
    marginVertical: 20, // 버튼 위아래 간격
    width: '100%', // 버튼 너비 전체로
  },
  forgotPassword: {
    marginVertical: 20,
    color: 'blue', // 변경 가능
  },
  button: {
    marginVertical: 20, // 버튼 위아래 간격
    width: '100%', // 버튼 너비 전체로
  },
});

export default LoginScreen;

