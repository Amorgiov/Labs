import * as React from 'react';
import { View, Text, Button } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import ButtonEx from './components/ButtonEx';
import ListEx from './components/ListEx';
import { BottomTabs } from 'react-native-screens';

function ButtonScreen({ navigation }) {
  return (
    <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
      <ButtonEx />

      <Button title="List" onPress={() => navigation.navigate('List')}></Button>
    </View>
  );
}

function ListScreen() {
  return (
    <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
      <ListEx />
    </View>
  );
}

const Stack = createNativeStackNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="Button">
        <Stack.Screen name="Button" component={ButtonScreen} />
        <Stack.Screen name="List" component={ListScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}
