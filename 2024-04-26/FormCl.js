import React, { useState } from 'react';
import { View, Text, TextInput, StyleSheet, Button, Alert } from 'react-native';

const FormCl = () => {
  const [formData, setFormData] = useState({
    date: '',
    day: '',
    contactName: '',
    phoneNumber: '',
    location: '',
    productType: '',
    productName: '',
    manufacturer: '',
    capacity: '',
    unit: '',
    quantity: '',
    totalAmount: '',
  });

  // 서버 주소
  const serverURL = 'http://your-server-address:your-port-number';

  const handleFormSubmit = () => {
    // 서버로 데이터 전송
    fetch(`${serverURL}/logistics`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData),
    })
      .then(response => {
        if (response.ok) {
          Alert.alert('Success', 'Data sent successfully.');
          // 성공적으로 전송되었을 때 필요한 처리를 추가할 수 있습니다.
        } else {
          throw new Error('Failed to send data.');
        }
      })
      .catch(error => {
        Alert.alert('Error', error.message);
      });
  };

  return (
    <View style={styles.container}>
      <Text style={styles.heading}>물류 운송 내역</Text>
      <View style={styles.itemContainer}>
        <Text style={styles.label}>일자:</Text>
        <TextInput
          style={styles.input}
          placeholder="YYYY/MM/DD"
          keyboardType="numeric"
          onChangeText={text => setFormData(prevState => ({ ...prevState, date: text }))}
        />
        <Text style={styles.label}>요일:</Text>
        <TextInput
          style={styles.input}
          placeholder="요일"
          onChangeText={text => setFormData(prevState => ({ ...prevState, day: text }))}
        />
      </View>
      {/* 나머지 입력 요소들도 유사하게 처리 */}
      <Button title="전송" onPress={handleFormSubmit} />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
  },
  heading: {
    fontSize: 20,
    fontWeight: 'bold',
    marginBottom: 10,
  },
  itemContainer: {
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
});

export default FormCl;
