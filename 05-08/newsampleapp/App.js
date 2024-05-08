/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
//import * as React from 'react';
import { 
  SafeAreaView, 
  ScrollView, 
  StatusBar, 
  StyleSheet, 
  Text, 
  useColorScheme, 
  View, 
  Button 
} from 'react-native';

import { 
  Colors, 
  DebugInstructions, 
  Header, 
  LearnMoreLinks, 
  ReloadInstructions 
} from 'react-native/Libraries/NewAppScreen';

import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import InitialScreen from './screens/initialScreen';
import LoginScreen from './screens/loginScreen';
import NaverLoginComponent from './screens/naverLoginScreen'; // 변경
import OrderScreen from './screens/orderScreen';

const Stack = createStackNavigator(); // 스택 내비게이터 생성

const Section = ({ children, title }) => {
  const isDarkMode = useColorScheme() === 'dark';
  return (
    <View style={styles.sectionContainer}>
      <Text
        style={[
          styles.sectionTitle,
          {
            color: isDarkMode ? Colors.white : Colors.black,
          },
        ]}>
        {title}
      </Text>
      <Text
        style={[
          styles.sectionDescription,
          {
            color: isDarkMode ? Colors.light : Colors.dark,
          },
        ]}>
        {children}
      </Text>
    </View>
  );
};

const App = () => {
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  return (
    <ScrollView
      contentInsetAdjustmentBehavior="automatic"
      style={backgroundStyle}
    >
      <SafeAreaView>
        <StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} />
        <NavigationContainer> {/* NavigationContainer는 SafeAreaView 바깥에 있어야 함 */}
          <Stack.Navigator initialRouteName="InitialScreen">
            <Stack.Screen name="InitialScreen" component={InitialScreen} />
            <Stack.Screen name="Login" component={LoginScreen} />
            <Stack.Screen name="OrderScreen" component={OrderScreen} /> 
            <Stack.Screen name="NaverLoginComponent" component={NaverLoginComponent} /> {/* 이름 변경 */}
          </Stack.Navigator>
        </NavigationContainer>
      </SafeAreaView>
    </ScrollView>
  );
  
};

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
  },
  highlight: {
    fontWeight: '700',
  },
});

export default App;
