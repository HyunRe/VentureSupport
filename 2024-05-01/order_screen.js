import React, { useEffect, useState } from 'react';
import { ScrollView, StyleSheet } from 'react-native';
import { SearchBox } from "./SearchBox";
import { SearchResults } from "./SearchResults";
import { useDebouncedState } from "./useDebouncedState";
import FormInputCl from "./FormInputCl";
import { useNavigation } from '@react-navigation/native';

export function OrderScreen({ value, delay = 500 }) {
  const [searchTerm, setSearchTerm] = useState(value);
  const [debouncedSearchTerm, setDebouncedSearchTerm] = useDebouncedState(searchTerm, delay);
  const [formList, setFormList] = useState([]);

  useEffect(() => {
    setDebouncedSearchTerm(value);
  }, [value, setDebouncedSearchTerm]);

  useEffect(() => {
    // 페이지가 열리자마자 폼 데이터를 받아오는 비동기 함수를 호출합니다.
    const fetchData = async () => {
      try {
        // 여기에서 폼 데이터를 받아오는 비동기 작업을 수행합니다.
        const formData = await fetchFormData(); // fetchFormData 함수는 실제로 데이터를 받아오는 함수로 대체되어야 합니다.
        setFormList(prevList => [...prevList, formData]);
      } catch (error) {
        console.error('Error fetching form data:', error);
      }
    };

    fetchData(); // fetchData 함수를 호출하여 페이지가 열리자마자 폼 데이터를 받아옵니다.
  }, []); // 빈 배열로 설정하여 한 번만 실행되도록 합니다.

  useEffect(() => {
    // 예시 데이터를 formList에 추가합니다.
    setFormList([
      { deadline: '13:16', location: '경기도', productName: '비누', totalAmount: '13600' },
      { deadline: '15:07', location: '서울', productName: '샴푸', totalAmount: '15000' }
    ]);
  }, []); // 빈 배열로 설정하여 한 번만 실행되도록 합니다.


  return (
    <ScrollView contentContainerStyle={styles.container}>
      <SearchBox value={searchTerm} onChange={setSearchTerm} />
      <SearchResults searchTerm={debouncedSearchTerm} />
      {formList.map((formData, index) => (
        <FormInputCl key={index} route={{ params: formData }} /> // FormInputCl 컴포넌트를 호출하여 받아온 폼 데이터를 전달합니다.
      ))}
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
  },
});

