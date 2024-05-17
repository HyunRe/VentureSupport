import React from 'react';
import { TouchableOpacity, Text, StyleSheet } from 'react-native';
import { theme } from '../core/theme';

type Props = {
  mode?: 'outlined' | 'contained';
  style?: object;
  onPress?: () => void;
  children: React.ReactNode;
};

const Button = ({ mode = 'contained', style, onPress, children }: Props) => (
  <TouchableOpacity
    style={[
      styles.button,
      mode === 'outlined' && { backgroundColor: theme.colors.surface },
      style,
    ]}
    onPress={onPress}
  >
    <Text
      style={[
        styles.text,
        mode === 'contained' && { color: theme.colors.surface },
      ]}
    >
      {children}
    </Text>
  </TouchableOpacity>
);

const styles = StyleSheet.create({
  button: {
    width: '100%',
    marginVertical: 10,
    paddingVertical: 10,
    borderRadius: 20,
    justifyContent: 'center',
    alignItems: 'center',
    elevation: 2,
  },
  text: {
    fontSize: 16,
    fontWeight: 'bold',
  },
});

export default Button;
