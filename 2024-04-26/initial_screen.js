import React from 'react';
import { View, Text, TouchableOpacity, StyleSheet, Image } from 'react-native';
import { useNavigation } from '@react-navigation/native'; // navigation hook 추가

const logo = require('./path/to/logo.png');

const InitialScreen = () => {
  const navigation = useNavigation(); // navigation hook 추가

  return (
    <View style={styles.container}>
      <View style={styles.logoContainer}>
        {/* 로고 표시 */}
        <Image source={logo} style={styles.logo} />
      </View>
      <View style={styles.selectionContainer}>
        {/* "업종을 선택해주세요." 텍스트 */}
        <Text style={styles.selectionText}>업종을 선택해주세요.</Text>
      </View>
      <TouchableOpacity
        style={styles.buttonContainer}
        onPress={() => navigation.navigate('FormInputScreen')} 
      >
        {/* "업체 이용자" 버튼 */}
        <Text style={styles.buttonText}>업체 이용자</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.buttonContainer}
        onPress={() => navigation.navigate('LoginScreen')}
      >
        {/* "운송 기사" 버튼 */}
        <Text style={styles.buttonText}>운송 기사</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 100,
    paddingHorizontal: 20,
  },
  logoContainer: {
    marginBottom: 64,
    alignItems: 'center', // 로고 위치 수정
  },
  logo: {
    width: 100,
    height: 100,
  },
  selectionContainer: {
    marginBottom: 34,
    alignItems: 'center', // 텍스트 위치 수정
  },
  selectionText: {
    fontSize: 30,
    textAlign: 'center',
  },
  buttonContainer: {
    marginBottom: 16,
    backgroundColor: 'lightblue',
    borderRadius: 10,
    alignItems: 'center',
    justifyContent: 'center',
    paddingVertical: 10,
  },
  buttonText: {
    fontSize: 24,
  },
});

export default InitialScreen;
