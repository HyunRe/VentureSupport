import React, { useState } from 'react';
import { Alert } from 'react-native';

const loginCl = async (username, password) => {
  try {
    const response = await fetch('http://your-server-address/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ username, password }),
    });

    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      throw new Error('Failed to login.');
    }
  } catch (error) {
    Alert.alert('Error', error.message);
  }
};

export default loginCl;
