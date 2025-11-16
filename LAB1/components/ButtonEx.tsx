import React, { Component } from 'react';
import {
  StyleSheet,
  Text,
  View,
  TouchableHighlight,
  Image,
} from 'react-native';
class ButtonEx extends Component {
  constructor(props) {
    super(props);
    this.state = { pressing: false };
  }
  _onPressIn = () => {
    this.setState({ pressing: true });
  };
  _onPressOut = () => {
    this.setState({ pressing: false });
  };
  render() {
    return (
      <View style={styles.container}>
        <TouchableHighlight
          onPressIn={this._onPressIn}
          onPressOut={this._onPressOut}
          style={styles.touchable}
        >
          <View style={styles.button}>
            <Text style={styles.welcome}>
              {this.state.pressing ? (
                <Image
                  source={require('../res/images/sheet.png')}
                  style={styles.image}
                  resizeMode="contain"
                />
              ) : (
                'Не нажал'
              )}
            </Text>
          </View>
        </TouchableHighlight>
      </View>
    );
  }
}
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: { fontSize: 20, textAlign: 'center', margin: 10, color: '#FFFFFF' },
  touchable: { borderRadius: 16 },
  button: {
    backgroundColor: '#37ad19ff',
    borderRadius: 32,
    height: 100,
    width: 100,
    justifyContent: 'center',
  },
});
export default ButtonEx;
