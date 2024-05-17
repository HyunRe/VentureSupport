import React from 'react';
import { StyleSheet, ImageBackground } from 'react-native';

const Background = ({ children }: { children: React.ReactNode }) => (
  <ImageBackground source={require('../assets/background.png')} resizeMode="cover" style={styles.background}>
    {children}
  </ImageBackground>
);

const styles = StyleSheet.create({
  background: {
    flex: 1,
    width: '100%',
    alignItems: 'center',
    justifyContent: 'center',
  },
});

export default Background;
