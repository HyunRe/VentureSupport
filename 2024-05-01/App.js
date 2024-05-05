import React, { useState } from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { SafeAreaView, ScrollView, View, Text, Button } from 'react-native';
import NaverLogin from './naver_login';
import InitialScreen from './initial_screen';
import SellFormsScreen from './sell_forms_screen';
import LoginScreen from './login_screen';
import OrderScreen from './order_screen';

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
    </NavigationContainer>
  );
}









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