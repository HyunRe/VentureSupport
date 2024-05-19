import React from 'react';
import { View, Text, TextInput, StyleSheet, Button, Alert, ScrollView } from 'react-native';


const LogisticsCreationScreen = () => {
  const [소매가, set소매가] = React.useState('');
  const [상품수량, set상품수량] = React.useState('');
  const [총금액, set총금액] = React.useState('');

  const 계산하기 = () => {
    if (상품수량 && 소매가) {
      const total = parseFloat(상품수량) * parseFloat(소매가);
      set총금액(total.toFixed(2));
    }
  };

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <View style={styles.formContainer}>
        <Text style={styles.heading}>물류 운송 내역</Text>
        <View style={styles.itemContainer}>
          <Text style={styles.label}>기한:</Text>
          <TextInput style={styles.input} placeholder="YYYY/MM/DD" keyboardType="numeric" />
          <Text style={styles.label}>요일:</Text>
          <TextInput style={styles.input} placeholder="요일" />
        </View>
        <View style={styles.itemContainer}>
          <Text style={styles.label}>거래처 정보:</Text>
          <TextInput style={styles.input} placeholder="대표자 이름" />
          <TextInput style={styles.input} placeholder="전화번호" keyboardType="numeric" />
        </View>
        <View style={styles.itemContainer}>
          <Text style={styles.label}>거래처 위치:</Text>
          <TextInput style={styles.input} placeholder="위치" />
        </View>
        <View style={styles.itemContainer}>
          <Text style={styles.label}>상품 정보:</Text>
          <TextInput style={styles.input} placeholder="종류" />
          <TextInput style={styles.input} placeholder="이름" />
          <TextInput style={styles.input} placeholder="제조사명" />
          <TextInput style={styles.input} placeholder="용량" keyboardType="numeric" />
          <TextInput style={styles.input} placeholder="단위" />
        </View>
        <View style={styles.itemContainer}>
          <Text style={styles.label}>상품 수량:</Text>
          <TextInput
            style={styles.input}
            placeholder="수량"
            keyboardType="numeric"
            value={상품수량}
            onChangeText={(text) => set상품수량(text)}
            onBlur={계산하기}
          />
        </View>
        <View style={styles.itemContainer}>
          <Text style={styles.label}>소매가:</Text>
          <TextInput
            style={styles.input}
            placeholder="소매가"
            keyboardType="numeric"
            value={소매가}
            onChangeText={(text) => set소매가(text)}
            onBlur={계산하기}
          />
        </View>
        <View style={styles.itemContainer}>
          <Text style={styles.label}>총 금액:</Text>
          <TextInput style={styles.input} placeholder="금액" value={총금액} />
        </View>
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
  formContainer: { 
    paddingBottom: 20, 
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

export default LogisticsCreationScreen;