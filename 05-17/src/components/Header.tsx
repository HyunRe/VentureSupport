import React from 'react';
import { StyleSheet, View, Text } from 'react-native';

type Props = {
  children: string;
};

const Header = ({ children }: Props) => (
  <View style={styles.header}>
    <Text style={styles.title}>{children}</Text>
  </View>
);

const styles = StyleSheet.create({
  header: {
    width: '100%',
    height: 80,
    paddingTop: 36,
    backgroundColor: '#f7287b',
    alignItems: 'center',
    justifyContent: 'center',
  },
  title: {
    color: 'white',
    fontSize: 18,
  },
});

export default Header;
