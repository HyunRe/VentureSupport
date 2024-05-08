import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, StyleSheet } from 'react-native';
import { useNavigation } from '@react-navigation/native';

const OrderScreen = () => {
  const [order, setOrder] = useState('');
  const navigation = useNavigation();

  const handleSubmit = () => {
    // 여기에 주문을 제출하는 로직을 추가하세요.
    console.log('주문 제출:', order);
    // 주문을 제출한 후 다른 화면으로 이동하도록 네비게이션을 설정합니다.
    navigation.navigate('ConfirmationScreen');
  };

  return (
    <View style={styles.container}>
      <Text style={styles.label}>주문 정보:</Text>
      <TextInput
        style={styles.input}
        value={order}
        onChangeText={setOrder}
        placeholder="주문 내용을 입력하세요."
        multiline
      />
      <TouchableOpacity style={styles.button} onPress={handleSubmit}>
        <Text style={styles.buttonText}>주문 제출</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
  },
  label: {
    fontSize: 20,
    marginBottom: 10,
  },
  input: {
    height: 150,
    borderColor: 'gray',
    borderWidth: 1,
    borderRadius: 5,
    padding: 10,
    marginBottom: 20,
  },
  button: {
    backgroundColor: 'lightblue',
    paddingVertical: 15,
    borderRadius: 5,
    alignItems: 'center',
  },
  buttonText: {
    fontSize: 18,
  },
});

export default OrderScreen;
