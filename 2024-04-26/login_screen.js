import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, StyleSheet, ScrollView, Image } from 'react-native';
import { useNavigation } from '@react-navigation/native';

const logo = require('./path/to/logo.png');

const LoginScreen = ({ navigation }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loginError, setLoginError] = useState('');

  const handleLogin = async () => {
    try {
      // 서버로부터 이메일과 비밀번호를 받아오는 API 호출 (이 부분은 서버와의 통신에 맞게 수정 필요)
      const response = await fetch('서버의 URL', {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });
      const data = await response.json();

      // 사용자가 입력한 이메일과 비밀번호와 서버로부터 받아온 이메일과 비밀번호를 비교하여 로그인 성공 여부 판별
      if (data.success) {
        // 로그인 성공 시 다음 페이지로 이동 (이 부분은 navigation을 이용하여 적절히 구현 필요)
        navigation.navigate('OrderScreen');
      } else {
        // 로그인 실패 시 에러 메시지 표시
        setLoginError('이메일 또는 비밀번호가 올바르지 않습니다.');
      }
    } catch (error) {
      console.error('로그인 에러:', error);
      setLoginError('로그인 중 오류가 발생했습니다.');
    }
  };

  return (
    <ScrollView contentContainerStyle={styles.scrollViewContent}>
      <View style={styles.container}>
        <Text style={styles.loginText}>로그인</Text>
        <TextInput
          style={styles.input}
          placeholder="이메일"
          keyboardType="email-address"
          autoCapitalize="none"
          value={email}
          onChangeText={setEmail}
        />
        <TextInput
          style={styles.input}
          placeholder="비밀번호"
          secureTextEntry={true}
          value={password}
          onChangeText={setPassword}
        />
        <TouchableOpacity style={styles.loginButton} onPress={handleLogin}>
          <Text style={styles.buttonText}>로그인</Text>
        </TouchableOpacity>
        {loginError ? <Text style={styles.errorText}>{loginError}</Text> : null}
        <TouchableOpacity style={styles.link}>
          <Text>비밀번호를 잊으셨나요?</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.link}>
          <Text>다른 로그인 방법</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.loginButton} onPress={handleNaverLogin}>
          <Text style={styles.buttonText}>네이버 로그인</Text>
        </TouchableOpacity>
        {/* 뒤로 가기 버튼 */}
        <TouchableOpacity onPress={() => navigation.goBack()}>
          <Image source={logo} style={styles.logo} />
        </TouchableOpacity>
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  scrollViewContent: {
    flexGrow: 1,
    justifyContent: 'center',
    paddingTop: 64,
    paddingBottom: 32,
  },
  container: {
    justifyContent: 'center',
    alignItems: 'center',
    paddingHorizontal: 20,
  },
  loginText: {
    fontSize: 34,
    fontWeight: 'bold',
    marginBottom: 34,
  },
  input: {
    width: '100%',
    height: 40,
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 5,
    marginBottom: 10,
    paddingHorizontal: 10,
  },
  loginButton: {
    backgroundColor: 'blue',
    width: '100%',
    height: 40,
    borderRadius: 5,
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: 10,
  },
  buttonText: {
    color: 'white',
    fontWeight: 'bold',
  },
  link: {
    marginBottom: 10,
  },
  logo: {
    width: 100,
    height: 100,
  },
  errorText: {
    color: 'red',
    marginBottom: 10,
  },
});

export default LoginScreen;
