import React, { useState } from 'react';
import { SafeAreaView, Button, View, Text, ScrollView } from 'react-native';
import NaverLogin from '@react-native-seoul/naver-login';
import { useNavigation } from '@react-navigation/native';

const consumerKey = 'bPklbFZr9oDS49tlE6NP';
const consumerSecret = '9UF9PHRqKf';
const appName = 'able';
const serviceUrlScheme = 'navertest';

const NaverLoginComponent = () => {
  const navigation = useNavigation();
  const [success, setSuccessResponse] = useState();
  const [failure, setFailureResponse] = useState();
  const [getProfileRes, setGetProfileRes] = useState();

  const login = async () => {
    try {
      const { failureResponse, successResponse } = await NaverLogin.login({
        appName,
        consumerKey,
        consumerSecret,
        serviceUrlScheme,
      });
      setSuccessResponse(successResponse);
      setFailureResponse(failureResponse);
      // 네이버 로그인 성공 시 OrderScreen.js로 이동
      if (successResponse) {
        navigation.navigate('OrderScreen');
      }
    } catch (error) {
      console.error(error);
    }
  };

  const logout = async () => {
    try {
      await NaverLogin.logout();
      setSuccessResponse(undefined);
      setFailureResponse(undefined);
      setGetProfileRes(undefined);
    } catch (error) {
      console.error(error);
    }
  };

  const getProfile = async () => {
    try {
      const profileResult = await NaverLogin.getProfile(success?.accessToken);
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
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <SafeAreaView style={{ alignItems: 'center', justifyContent: 'center', flex: 1 }}>
      <ScrollView style={{ flex: 1 }} contentContainerStyle={{ flexGrow: 1, padding: 24 }}>
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
};

const Gap = () => <View style={{ marginTop: 24 }} />;
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

export default NaverLoginComponent;
