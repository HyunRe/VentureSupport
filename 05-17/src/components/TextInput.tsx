import React from 'react';
import { TextInput as RNTextInput, StyleSheet } from 'react-native';
import { theme } from '../core/theme';

type Props = React.ComponentProps<typeof RNTextInput>;

const TextInput = ({ style, ...props }: Props) => (
  <RNTextInput
    style={[styles.input, style]}
    placeholderTextColor={theme.colors.placeholder}
    {...props}
  />
);

const styles = StyleSheet.create({
  input: {
    backgroundColor: theme.colors.surface,
    width: '100%',
    paddingVertical: 12,
    paddingHorizontal: 16,
    borderRadius: 8,
    color: theme.colors.primary,//theme.colors.text,
    marginBottom: 8,
    borderWidth: 1,
    borderColor: theme.colors.primary,
  },
});

export default TextInput;
