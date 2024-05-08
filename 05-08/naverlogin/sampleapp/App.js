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


/*
const App = () => {
  return (
    <View style={{ flex: 1 }}>
      <InitialScreen />
    </View>
  );
};

export default App;
*/


/*import 'react-native';
import React from 'react';
import App from './App';

// Note: import explicitly to use the types shipped with jest.
import {it} from '@jest/globals';

// Note: test renderer must be required after react-native.
import renderer from 'react-test-renderer';

it('renders correctly', () => {
  renderer.create(<App />);
});
*/


/*import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
//import { createStackNavigator } from '@react-navigation/stack';
import InitialScreen from './android/initial_screen';
import SellFormsScreen from './src/sell_forms_screen';
import LoginScreen from './android/login_screen';
import OrderScreen from './android/order_screen';
//import NaverLoginComponent from './android/naver_login';


/*
import React, { useState } from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { SafeAreaView, ScrollView, View, Text, Button } from 'react-native';
import NaverLogin from './logins/naver_login';
import InitialScreen from './pages/initial_screen';
import SellFormsScreen from './sell_forms_screen';
import LoginScreen from './logins/login_screen';
import OrderScreen from './pages/order_screen';

const Stack = createStackNavigator();

export default function App() {
  const [success, setSuccessResponse] = useState();
  const [failure, setFailureResponse] = useState();
  const [getProfileRes, setGetProfileRes] = useState();

  const login = async () => {
    const {failureResponse, successResponse} = await NaverLogin.login({
        appName,
        consumerKey,
        consumerSecret,
        serviceUrlScheme,
      });
      setSuccessResponse(successResponse);
      setFailureResponse(failureResponse);
  };

  const logout = async () => {
    try {
        await NaverLogin.logout();
        setSuccessResponse(undefined);
        setFailureResponse(undefined);
        setGetProfileRes(undefined);
      } catch (e) {
        console.error(e);
      }
  };

  const getProfile = async () => {
    try {
        const profileResult = await NaverLogin.getProfile(success.accessToken);
        setGetProfileRes(profileResult);
      } catch (error) {
        console.error(error);
        setGetProfileRes(undefined);
      }
  };

  const deleteToken = async () => {
    try {
        await NaverLogin.deleteToken();
        setSuccessResponse(undefined);
        setFailureResponse(undefined);
        setGetProfileRes(undefined);
      } catch (e) {
        console.error(e);
      }
  };

  const Gap = () => <View style={{marginTop: 24}} />;

  const ResponseJsonText = ({ json = {}, name }) => (
    <View
      style={{
        padding: 12,
        borderRadius: 16,
        borderWidth: 1,
        backgroundColor: '#242c3d',
      }}>
      <Text style={{ fontSize: 20, fontWeight: 'bold', color: 'white' }}>
        {name}
      </Text>
      <Text style={{ color: 'white', fontSize: 13, lineHeight: 20 }}>
        {JSON.stringify(json, null, 4)}
      </Text>
    </View>
  );

  return (
    <NavigationContainer>
        <Stack.Navigator initialRouteName="InitialScreen">
          <Stack.Screen name="InitialScreen" component={InitialScreen} />
          <Stack.Screen name="SellForms" component={SellFormsScreen} />
          <Stack.Screen name="Login" component={LoginScreen} />
          <Stack.Screen name="OrderScreen" component={OrderScreen} /> 
          <Stack.Screen name="FormInputCl" component={FormInputCl} />
           
        </Stack.Navigator>
      
    </NavigationContainer>
  );
}
*/








/*
// App.js
import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { SafeAreaView, ScrollView, View, Text, Button } from 'react-native';
import NaverLogin from 'react-native-naver-login';
import InitialScreen from './initial_screen';
import SellFormsScreen from './sell_forms_screen';
import LoginScreen from './login_screen';

const Stack = createStackNavigator();

export default function App() {
  const [success, setSuccessResponse] = useState();
  const [failure, setFailureResponse] = useState();
  const [getProfileRes, setGetProfileRes] = useState();

  return (
    <NavigationContainer>
        <Stack.Navigator initialRouteName="InitialScreen">
          <Stack.Screen name="InitialScreen" component={InitialScreen} />
          <Stack.Screen name="SellForms" component={SellFormsScreen} />
          <Stack.Screen name="Login" component={LoginScreen} />
        </Stack.Navigator>
    </NavigationContainer>
  );
}

  const login = async () => {
    const {failureResponse, successResponse} = await NaverLogin.login({
      appName,
      consumerKey,
      consumerSecret,
      serviceUrlScheme,
    });
    setSuccessResponse(successResponse);
    setFailureResponse(failureResponse);
  };

  const logout = async () => {
    try {
      await NaverLogin.logout();
      setSuccessResponse(undefined);
      setFailureResponse(undefined);
      setGetProfileRes(undefined);
    } catch (e) {
      console.error(e);
    }
  };

  const getProfile = async () => {
    try {
      const profileResult = await NaverLogin.getProfile(success.accessToken);
      setGetProfileRes(profileResult);
    } catch (error) {
      console.error(error);
      setGetProfileRes(undefined);
    }
  };
  

  const deleteToken = async () => {
    try {
      await NaverLogin.deleteToken();
      setSuccessResponse(undefined);
      setFailureResponse(undefined);
      setGetProfileRes(undefined);
    } catch (e) {
      console.error(e);
    }
  };

  return (
    <SafeAreaView
      style={{alignItems: 'center', justifyContent: 'center', flex: 1}}>
      <ScrollView
        style={{flex: 1}}
        contentContainerStyle={{flexGrow: 1, padding: 24}}>
        <Button title={'Login'} onPress={login} />
        <Gap />
        <Button title={'Logout'} onPress={logout} />
        <Gap />
        {success ? (
          <>
            <Button title="Get Profile" onPress={getProfile} />
            <Gap />
          </>
        ) : null}
        {success ? (
          <View>
            <Button title="Delete Token" onPress={deleteToken} />
            <Gap />
            <ResponseJsonText name={'Success'} json={success} />
          </View>
        ) : null}
        <Gap />
        {failure ? <ResponseJsonText name={'Failure'} json={failure} /> : null}
        <Gap />
        {getProfileRes ? (
          <ResponseJsonText name={'GetProfile'} json={getProfileRes} />
        ) : null}
      </ScrollView>
    </SafeAreaView>
  );

const Gap = () => <View style={{marginTop: 24}} />;

const ResponseJsonText = ({ json = {}, name }) => (
  <View
    style={{
      padding: 12,
      borderRadius: 16,
      borderWidth: 1,
      backgroundColor: '#242c3d',
    }}>
    <Text style={{ fontSize: 20, fontWeight: 'bold', color: 'white' }}>
      {name}
    </Text>
    <Text style={{ color: 'white', fontSize: 13, lineHeight: 20 }}>
      {JSON.stringify(json, null, 4)}
    </Text>
  </View>
);






 */