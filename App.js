import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, StyleSheet, ScrollView, Image, Button } from 'react-native';
//import NaverLoginComponent from './logins/naver_login';
import { useNavigation } from '@react-navigation/native';

// 로그인 화면을 나타내는 컴포넌트입니다.
const LoginScreen = ({ navigation }) => {

  // 이메일과 비밀번호를 관리하기 위한 상태 변수들입니다.
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  // 로그인 시 발생하는 에러를 관리하기 위한 상태 변수입니다.
  const [loginError, setLoginError] = useState('');

  // 로그인 상태를 관리하는 상태 변수입니다.
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  // 로그인 버튼을 눌렀을 때 실행되는 함수입니다.
  const handleLogin = async () => {
    const url = 'http://192.168.219.100:8080/login';

    try {
      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          email: email,
          password: password,
        }),
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      const data = await response.json();
      console.log('Login response:', data);

      // 서버로부터 받은 응답을 처리합니다.
      if (data.success !== undefined) {
        // 성공적인 응답 처리
      } else {
        // 실패 응답 처리
      }
    } catch (error) {
      // 로그인 과정에서 발생한 에러를 처리합니다.
      console.error('Login error:', error);
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
    fontSize: 24,
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
