import React from "react";
import { Text } from "react-native";
import { NavigationContainer } from "@react-navigation/native";
import { createStackNavigator } from "@react-navigation/stack";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";

import InitialScreen from "./InitialScreen";
import LogisticsCreationScreen from "./LogisticsCreationScreen";
import LoginScreen from "./LoginScreen";
/*
import SignUpScreen from "./SignUpScreen";
import HomeScreen from "./HomeScreen";
import HomeDetailScreen from "./HomeDetailScreen";
import MapScreen from "./MapScreen";
import SchedulerScreen from "./SchedulerScreen";
import ProfileScreen from "./ProfileScreen";
import IncomeExpenseScreen from "./IncomeExpenseScreen";
import IncomeChartScreen from "./IncomeChartScreen";
import ExpenseChartScreen from "./ExpenseChartScreen";
*/

const Stack = createStackNavigator();
const Tab = createBottomTabNavigator();

/*
const LoginStack = () => (
    <Stack.Navigator initialRouteName="LoginScreen">
      <Stack.Screen name="Login" component={LoginScreen} options={{ title: "login" }} />
      <Stack.Screen name="Signup" component={SignUpScreen} />
    </Stack.Navigator>
  );
*/

/*
const HomeStack = () => (
    <Stack.Navigator initialRouteName="HomeScreen">
    <Stack.Screen name="Home" component={HomeScreen} options={{ title: "home" }} />
    <Stack.Screen name="Detail" component={HomeDetailScreen} />
  </Stack.Navigator>
);

const ProfileStack = () => (
  <Stack.Navigator initialRouteName="ProfileScreen">
    <Stack.Screen name="Profile" component={ProfileScreen} options={{ title: "profile" }} />
    <Stack.Screen name="IncomeExpense" component={IncomeExpenseScreen} />
    <Stack.Screen name="IncomeChart" component={IncomeChartScreen} />
    <Stack.Screen name="ExpenseChart" component={ExpenseChartScreen} />
  </Stack.Navigator>
);

const TabNavigator = () => (
  <Tab.Navigator
    screenOptions={{
      tabBarStyle: {
        backgroundColor: "#fff", // 배경색 설정
      },
      tabBarActiveTintColor: "#46c3ad", // 활성 탭 색상 설정
      tabBarInactiveTintColor: "#888", // 비활성 탭 색상 설정
    }}
  >
    <Tab.Screen
      name="Home"
      component={HomeStack}
      options={{
        tabBarLabel: '홈',
      }}
    />
    <Tab.Screen
      name="Map"
      component={MapScreen}
      options={{
        tabBarLabel: '지도',
      }}
    />
    <Tab.Screen
      name="Scheduler"
      component={SchedulerScreen}
      options={{
        tabBarLabel: '스케줄러',
      }}
    />
    <Tab.Screen
      name="Profile"
      component={ProfileStack}
      options={{
        tabBarLabel: '내정보',
      }}
    />
  </Tab.Navigator>
);
*/

const AppStack = () => (
  <Stack.Navigator initialRouteName="InitialScreen">
    <Stack.Screen name="InitialScreen" component={InitialScreen} />
    <Stack.Screen name="LogisticsCreationScreen" component={LogisticsCreationScreen} />
    <Stack.Screen name="LoginScreen" component={LoginScreen} />
  </Stack.Navigator>
);

const AppContainer = () => (
  <NavigationContainer>
    <AppStack />
  </NavigationContainer>
);

export default AppContainer;
