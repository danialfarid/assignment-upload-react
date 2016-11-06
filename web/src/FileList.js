import React, {Component} from "react";
import Config from "./Config"

class FileList extends Component {
  constructor(props) {
    super(props);
    this.state = {files: []};
    this.handleRefresh = this.handleRefresh.bind(this);
    this.handleRefresh();
  }

  handleRefresh() {
    var xhr = new XMLHttpRequest();
    xhr.open('get', Config.serverUrl);
    xhr.send();
    xhr.onload = () => {
      this.setState({
        files: JSON.parse(xhr.responseText).map(f => this.formatDate(f))
      });
    };
  }

  formatDate(f) {
    if (f.createDate) {
      var date = new Date(f.createDate);
      f.createDate = date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + date.getDate();
    }
    return f;
  }

  render() {
    return (
      <div className="list">
        Uploaded Files:
        <button onClick={this.handleRefresh}>Refresh</button>
        <ul>
          {this.state.files.map((f) => {
            return <li key={f.id}>{f.title}, {f.description}, {f.createDate},
              <a target="_blank" href={Config.serverUrl + '/' + f.id + '/file'}>{f.fileName}</a></li>;
          })}
        </ul>
      </div>
    );
  }
}

export default FileList;
