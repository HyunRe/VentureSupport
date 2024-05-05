import React from 'react';
import { View, Text, FlatList, StyleSheet } from 'react-native';

const SearchResults = ({ results }) => {
  return (
    <View style={styles.container}>
      <Text style={styles.heading}>Search Results</Text>
      <FlatList
        data={results}
        renderItem={({ item }) => (
          <View style={styles.item}>
            <Text>{item.title}</Text>
            <Text>{item.description}</Text>
          </View>
        )}
        keyExtractor={item => item.id.toString()}
      />
    </View>
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
  item: {
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 5,
    padding: 10,
    marginBottom: 10,
  },
});

export default SearchResults;
