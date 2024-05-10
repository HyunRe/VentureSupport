import React from 'react';
import { View, Text, TextInput, ScrollView, Button, TouchableOpacity, StyleSheet } from 'react-native';
//import { useNavigation } from '@react-navigation/native';

const SignUpScreen = () => {

  const handleConfirmButtonPress = () => {
    // 로그인 페이지로 이동
    //navigation.navigate('Login');
    console.log('로그인 페이지로 이동 또는 뒤로 가기');
  };
  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Text style={styles.heading}>회원가입</Text>
      <View style={styles.inputContainer}>
      <Text style={styles.label}>아이디:</Text>
        <TextInput style={styles.input} placeholder="아이디를 입력하세요" />
      </View>
      <View style={styles.inputContainer}>
        <Text style={styles.label}>비밀번호:</Text>
        <TextInput style={styles.input} placeholder="비밀번호를 입력하세요" secureTextEntry={true} />
      </View>
      <View style={styles.inputContainer}>
        <Text style={styles.label}>이름:</Text>
        <TextInput style={styles.input} placeholder="이름을 입력하세요" />
      </View>
      <View style={styles.inputContainer}>
        <Text style={styles.label}>사업자번호:</Text>
        <TextInput style={styles.input} placeholder="사업자번호를 입력하세요" keyboardType="numeric" />
      </View>
      {/* 추가 입력란 */}
      <View style={styles.inputContainer}>
        <Text style={styles.label}>개인번호:</Text>
        <TextInput style={styles.input} placeholder="개인번호를 입력하세요" keyboardType="numeric" />
        <TouchableOpacity style={styles.addButton}>
          <Text style={styles.addButtonLabel}>+</Text>
        </TouchableOpacity>
      </View>
      <View style={styles.inputContainer}>
        <Text style={styles.label}>인증번호:</Text>
        <TextInput style={styles.input} placeholder="인증번호를 입력하세요" keyboardType="numeric" />
      </View>
      <View style={styles.inputContainer}>
        <Text style={styles.label}>업종:</Text>
        <TextInput style={styles.input} placeholder="업종을 입력하세요" />
      </View>
      {/* 추가 입력란 */}
      <View style={styles.inputContainer}>
        <Text style={styles.label}>업체번호:</Text>
        <TextInput style={styles.input} placeholder="업체번호를 입력하세요" keyboardType="numeric" />
        <TouchableOpacity style={styles.addButton}>
          <Text style={styles.addButtonLabel}>+</Text>
        </TouchableOpacity>
      </View>
      <View style={styles.inputContainer}>
        <Text style={styles.label}>주소:</Text>
        <TextInput style={styles.input} placeholder="주소를 입력하세요" />
      </View>
      <Button
        title="확인"
        onPress={handleConfirmButtonPress}
        style={styles.confirmButton}
        color="lightblue"
      />
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    padding: 20,
  },
  heading: {
    fontSize: 20,
    fontWeight: 'bold',
    textAlign: 'center',
    marginBottom: 20,
  },
  inputContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 10,
  },
  label: {
    fontSize: 14,
    width: 100,
  },
  input: {
    flex: 1,
    borderWidth: 1,
    borderColor: '#ccc',
    paddingHorizontal: 10,
    fontSize: 14,
  },
  addButton: {
    marginLeft: 10,
    padding: 5,
    backgroundColor: '#ccc',
    borderRadius: 5,
  },
  addButtonLabel: {
    fontSize: 14,
  },
  confirmButton: {
    backgroundColor: 'blue',
    padding: 15,
    borderRadius: 5,
    alignItems: 'center',
    marginTop: 20,
  },
  confirmButtonText: {
    fontSize: 16,
    color: 'white',
  },
});

export default SignUpScreen;
