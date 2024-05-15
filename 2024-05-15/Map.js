import React from 'react';
import { SafeAreaView, StyleSheet } from 'react-native';
import NaverMapView, { Marker } from 'react-native-nmap';

const App = () => {
  return (
    <SafeAreaView style={styles.container}>
      <NaverMapView
        style={styles.map}
        center={{ latitude: 37.564362, longitude: 126.977011, zoom: 10 }}
      >
        <Marker coordinate={{ latitude: 37.564362, longitude: 126.977011 }} />
      </NaverMapView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  map: {
    width: '100%',
    height: '100%',
  },
});

export default App;