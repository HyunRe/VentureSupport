import React, { useState } from 'react';
import { View, Text, TextInput, StyleSheet, Button, Alert, ScrollView } from 'react-native';
import { useNavigation } from '@react-navigation/native';

const FormInputCl = () => {
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
    retailPrice: '',
    totalAmount: '',
  });

  useEffect(() => {
    if (route.params) {
      const { deadline, location, productName, totalAmount } = route.params;
      if (deadline) setFormData(prevState => ({ ...prevState, date: deadline }));
      if (location) setFormData(prevState => ({ ...prevState, location }));
      if (productName) setFormData(prevState => ({ ...prevState, productName }));
      if (totalAmount) setFormData(prevState => ({ ...prevState, totalAmount }));
    }
  }, [route.params]);

  const handleInputChange = (name, value) => {
    setFormData(prevState => ({
      ...prevState,
      [name]: value,
    }));
  };

  const calculateTotalAmount = () => {
    const { quantity, retailPrice } = formData;
    if (quantity && retailPrice) {
      const total = parseFloat(quantity) * parseFloat(retailPrice);
      handleInputChange('totalAmount', total.toFixed(2));
    }
  };

  const handleFormSubmit = () => {
    const serverURL = 'http://your-server-address:your-port-number';
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
    <ScrollView contentContainerStyle={styles.container}>
      <View style={styles.formContainer}>
        <Text style={styles.heading}>물류 운송 내역</Text>
        <View style={styles.itemContainer}>
          <Text style={styles.label}>기한:</Text>
          <TextInput
            style={styles.input}
            placeholder="YYYY/MM/DD"
            keyboardType="numeric"
            value={formData.date}
            onChangeText={text => handleInputChange('date', text)}
          />
          <Text style={styles.label}>요일:</Text>
          <TextInput
            style={styles.input}
            placeholder="요일"
            value={formData.day}
            onChangeText={text => handleInputChange('day', text)}
          />
        </View>
        <View style={styles.itemContainer}>
          <Text style={styles.label}>거래처 정보:</Text>
          <TextInput
            style={styles.input}
            placeholder="대표자 이름"
            value={formData.contactName}
            onChangeText={text => handleInputChange('contactName', text)}
          />
          <TextInput
            style={styles.input}
            placeholder="전화번호"
            keyboardType="numeric"
            value={formData.phoneNumber}
            onChangeText={text => handleInputChange('phoneNumber', text)}
          />
        </View>
        <View style={styles.itemContainer}>
          <Text style={styles.label}>거래처 위치:</Text>
          <TextInput
            style={styles.input}
            placeholder="위치"
            value={formData.location}
            onChangeText={text => handleInputChange('location', text)}
          />
        </View>
        <View style={styles.itemContainer}>
          <Text style={styles.label}>상품 정보:</Text>
          <TextInput
            style={styles.input}
            placeholder="종류"
            value={formData.productType}
            onChangeText={text => handleInputChange('productType', text)}
          />
          <TextInput
            style={styles.input}
            placeholder="품목명"
            value={formData.productName}
            onChangeText={text => handleInputChange('productName', text)}
          />
          <TextInput
            style={styles.input}
            placeholder="제조사명"
            value={formData.manufacturer}
            onChangeText={text => handleInputChange('manufacturer', text)}
          />
          <TextInput
            style={styles.input}
            placeholder="용량"
            keyboardType="numeric"
            value={formData.capacity}
            onChangeText={text => handleInputChange('capacity', text)}
          />
          <TextInput
            style={styles.input}
            placeholder="단위"
            value={formData.unit}
            onChangeText={text => handleInputChange('unit', text)}
          />
        </View>
        <View style={styles.itemContainer}>
          <Text style={styles.label}>상품 수량:</Text>
          <TextInput
            style={styles.input}
            placeholder="수량"
            keyboardType="numeric"
            value={formData.quantity}
            onChangeText={text => handleInputChange('quantity', text)}
            onBlur={calculateTotalAmount}
          />
        </View>
        <View style={styles.itemContainer}>
          <Text style={styles.label}>소매가:</Text>
          <TextInput
            style={styles.input}
            placeholder="소매가"
            keyboardType="numeric"
            value={formData.retailPrice}
            onChangeText={text => handleInputChange('retailPrice', text)}
            onBlur={calculateTotalAmount}
          />
        </View>
        <View style={styles.itemContainer}>
          <Text style={styles.label}>총 금액:</Text>
          <TextInput
            style={styles.input}
            placeholder="금액"
            value={formData.totalAmount}
            editable={false}
          />
        </View>
        <Button title="전송" onPress={handleFormSubmit} />
      </View>
    </ScrollView>
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

export default FormInputCl;
