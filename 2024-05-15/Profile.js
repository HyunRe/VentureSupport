import React from 'react';
import { View, Text, Button, StyleSheet, TouchableOpacity } from 'react-native';
import { withNavigation } from '@react-navigation/native';

const ProfileScreen = ({ navigation }) => {
  // 사용자 정보 (예시)
  const userInfo = {
    name: 'John Doe',
    email: 'johndoe@example.com',
  };

  // 통계 버튼 클릭 시 처리 함수
  const handleStatisticsPress = () => {
    // 네비게이션을 통해 통계 화면으로 이동합니다.
    navigation.navigate('Statistics');
  };

  return (
    <View style={styles.container}>
      <Text style={styles.label}>Name:</Text>
      <Text style={styles.text}>{userInfo.name}</Text>
      <Text style={styles.label}>Email:</Text>
      <Text style={styles.text}>{userInfo.email}</Text>
      
      {/* 통계 버튼 */}
      <TouchableOpacity onPress={handleStatisticsPress} style={styles.button}>
        <Text style={styles.buttonText}>통계</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    paddingHorizontal: 20,
  },
  label: {
    fontSize: 18,
    fontWeight: 'bold',
    marginBottom: 5,
  },
  text: {
    fontSize: 16,
    marginBottom: 15,
  },
  button: {
    backgroundColor: '#3498db',
    padding: 10,
    borderRadius: 5,
    marginTop: 20,
  },
  buttonText: {
    color: '#fff',
    fontSize: 18,
    fontWeight: 'bold',
  },
});

export default withNavigation(ProfileScreen);
