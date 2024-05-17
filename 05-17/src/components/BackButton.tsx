import React from 'react';
import { TouchableOpacity, Image, StyleSheet } from 'react-native';
import { theme } from '../core/theme';

type Props = {
  goBack: () => void;
};

const BackButton = ({ goBack }: Props) => (
  <TouchableOpacity onPress={goBack} style={styles.container}>
    <Image source={require('../assets/arrow_back.png')} style={styles.image} />
  </TouchableOpacity>
);

const styles = StyleSheet.create({
  container: {
    position: 'absolute',
    top: 60,
    left: 20,
  },
  image: {
    width: 24,
    height: 24,
    tintColor: theme.colors.primary,
  },
});

export default BackButton;
