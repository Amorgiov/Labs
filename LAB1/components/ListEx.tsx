import React, { Component } from 'react';
import {
  StyleSheet,
  Text,
  View,
  FlatList,
  Image,
  TouchableOpacity,
  Button,
} from 'react-native';

class ListEx extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [
        {
          id: '1',
          title: 'The Pragmatic Programmer',
          description: 'Классика про практику и философию разработки.',
          cover:
            'https://images-na.ssl-images-amazon.com/images/I/41as+WafrFL._SX258_BO1,204,203,200_.jpg',
        },
        {
          id: '2',
          title: 'Clean Code',
          description: 'Роберт Мартин о том, как писать читаемый код.',
          cover:
            'https://images-na.ssl-images-amazon.com/images/I/41xShlnTZTL._SX374_BO1,204,203,200_.jpg',
        },
        {
          id: '3',
          title: 'You Don’t Know JS',
          description: 'Серия книг о тонкостях JavaScript.',
          cover:
            'https://images-na.ssl-images-amazon.com/images/I/41jEbK-jG+L._SX331_BO1,204,203,200_.jpg',
        },
      ],
      selectedId: null, //храним idшник
      counter: 4,
    };
  }

  //Тут обрабатываем нажатие
  _onPressItem = id => {
    this.setState({ selectedId: id });
  };

  // Здесь добавление новой книги
  _addBook = () => {
    const newBook = {
      id: this.state.counter.toString(),
      title: `New Book #${this.state.counter}`,
      description: 'Это сгенерированная книга для примера.',
      cover: 'https://via.placeholder.com/100x150.png?text=Book',
    };
    this.setState(prev => ({
      data: [...prev.data, newBook],
      counter: prev.counter + 1,
    }));
  };

  _renderItem = ({ item }) => {
    const isSelected = this.state.selectedId === item.id;

    return (
      <TouchableOpacity
        onPress={() => this._onPressItem(item.id)}
        activeOpacity={0.7}
      >
        <View style={[styles.card, isSelected && styles.cardSelected]}>
          <Image source={{ uri: item.cover }} style={styles.cover} />
          <Text style={styles.title} numberOfLines={2}>
            {item.title}
          </Text>
          <Text style={styles.desc} numberOfLines={3}>
            {item.description}
          </Text>
        </View>
      </TouchableOpacity>
    );
  };

  render() {
    return (
      <View style={styles.container}>
        <Button title="Добавить книгу" onPress={this._addBook} />
        <FlatList
          data={this.state.data}
          renderItem={this._renderItem}
          keyExtractor={item => item.id}
          horizontal
          showsHorizontalScrollIndicator={false}
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    paddingVertical: 0,
    backgroundColor: '#F5FCFF',
  },
  card: {
    width: 140,
    height: 250,
    marginHorizontal: 8,
    backgroundColor: '#F5FCFF',
    borderRadius: 8,
    alignItems: 'center',
    padding: 8,
  },
  cardSelected: {
    backgroundColor: '#d1e7ff',
  },
  cover: {
    width: 100,
    height: 150,
    resizeMode: 'cover',
    borderRadius: 4,
    marginBottom: 8,
  },
  title: {
    fontSize: 14,
    fontWeight: 'bold',
    textAlign: 'center',
    marginBottom: 4,
  },
  desc: {
    fontSize: 12,
    color: '#555',
    textAlign: 'center',
  },
});

export default ListEx;
