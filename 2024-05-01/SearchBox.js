// SearchBox.js

import React from 'react';
import { TextInput, StyleSheet } from 'react-native';

const SearchBox = ({ value, onChange }) => {
  return (
    <TextInput
      style={styles.input}
      placeholder="검색어를 입력하세요"
      value={value}
      onChangeText={onChange}
    />
  );
};

const styles = StyleSheet.create({
  input: {
    height: 40,
    borderColor: 'gray',
    borderWidth: 1,
    borderRadius: 5,
    paddingHorizontal: 10,
    marginBottom: 20,
  },
});

export { SearchBox };
