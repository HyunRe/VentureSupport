import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, StyleSheet } from 'react-native';

// 예시용 데이터. 실제로는 데이터베이스에서 가져와야 합니다.
const sampleData = [
  { id: 1, title: 'Meeting', date: '2024-05-10', time: '10:00 AM' },
  { id: 2, title: 'Lunch', date: '2024-05-10', time: '12:00 PM' },
  { id: 3, title: 'Gym', date: '2024-05-10', time: '4:00 PM' },
];

const App = () => {
  const [schedule, setSchedule] = useState([]);

  useEffect(() => {
    // 예시용 데이터를 useState를 통해 설정합니다.
    setSchedule(sampleData);
  }, []);

  const renderItem = ({ item }) => (
    <View style={styles.item}>
      <Text style={styles.title}>{item.title}</Text>
      <Text style={styles.text}>Date: {item.date}</Text>
      <Text style={styles.text}>Time: {item.time}</Text>
    </View>
  );

  return (
    <View style={styles.container}>
      <Text style={styles.header}>Schedule</Text>
      <FlatList
        data={schedule}
        renderItem={renderItem}
        keyExtractor={item => item.id.toString()}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginTop: 40,
    marginHorizontal: 20,
  },
  header: {
    fontSize: 28,
    fontWeight: 'bold',
    marginBottom: 20,
    textAlign: 'center',
    color: '#333',
  },
  item: {
    backgroundColor: '#f9c2ff',
    padding: 20,
    marginVertical: 8,
    borderRadius: 10,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 5,
    color: '#333',
  },
  text: {
    fontSize: 18,
    color: '#555',
  },
});

export default App;
