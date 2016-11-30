import React from 'react';
class CourseTable extends React.Component {
  constructor() {
    super();
    this.state = {courses: []};
  }

  componentDidMount() {
    fetch(`http://localhost:8080/api/courses`)
      .then(result=> {
        this.setState({courses: result.json()});
        console.log("courses: " + this.state.courses);
      });
  }

  render() {
    return (
      <table className="table">
        <tr>
          <th>"Name"</th>
          <th>Summary</th>
          <th>Weeks</th>
        </tr>
        <tr>
          {rows}
        </tr>
      </table>
    )
  }
}
