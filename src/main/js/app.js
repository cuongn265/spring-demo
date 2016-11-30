import React from 'react';
import ReactDOM from 'react-dom';
import 'whatwg-fetch';

class App extends React.Component {
  constructor() {
    super();
    this.state = {courses: []};
  }

  componentDidMount() {
    let self = this;
    let myHeaders = new Headers({
      "Content-Type": "application/json"
    });
    let myInit = {
      method: 'GET',
      headers: myHeaders,
      mode: 'cors',
      cache: 'default',
      credentials: 'same-origin'
    };
    fetch("http://localhost:8080/api/courses", myInit)
      .then(function (response) {
          return response.json()
        }
      ).then(function (courses) {
        self.setState({courses: courses});
      })
      .catch(function (error) {
        console.log('There has been a problem with your fetch operation: ' + error.message);
      });
  }

  render() {
    return (<div>Hello World</div>);
  }
}


ReactDOM.render(<App/>, document.getElementById('react'));


