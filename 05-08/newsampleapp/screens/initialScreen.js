import React from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';
import { useNavigation } from '@react-navigation/native'; // navigation hook 추가


const InitialScreen = () => {
  const navigation = useNavigation(); // navigation hook 추가

  return (
    <View style={styles.container}>
      <View style={styles.selectionContainer}>
        {/* "업종을 선택해주세요." 텍스트 */}
        <Text style={styles.selectionText}>업종을 선택해주세요.</Text>
      </View>
      <TouchableOpacity
        style={styles.buttonContainer}
        onPress={() => navigation.navigate('FormInputScreen')} 
      >
        {/* "업체 이용자" 버튼 */}
        <Text style={styles.buttonText}>소매업자</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.buttonContainer}
        onPress={() => navigation.navigate('LoginScreen')}
      >
        {/* "운송 기사" 버튼 */}
        <Text style={styles.buttonText}>도매업자</Text>
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
