import React, {Component} from "react";
import FileList from "./FileList";
import Config from "./Config";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {file: null, metadata: {}};
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(field, e) {
    var metadata = this.state.metadata || {};
    metadata[field] = e.target.value;
    this.setState(this.state);
  }

  handleFileChange(e) {
    this.setState({file: e.target.files && e.target.files[0]});
  }

  handleSubmit(e) {
    var xhr = new XMLHttpRequest();
    xhr.open('post', Config.serverUrl);
    var formData = new FormData();
    formData.append('file', this.state.file);
    formData.append('metadata', JSON.stringify(this.state.metadata));
    xhr.send(formData);
    xhr.onload = () => {
      this.refs.fileList.handleRefresh();
    };
  }

  render() {
    return (
      <div>
        <h2>Upload files</h2>
        <form>
          <label><span>Select File:</span> <input type="file" onChange={this.handleFileChange.bind(this)}/><br/></label>
          <label><span>Title:</span> <input type="text" onChange={this.handleChange.bind(this, 'title')}/></label><br/>
          <label><span>Description:</span> <input type="text"
                                                  onChange={this.handleChange.bind(this, 'description')}/></label><br/>
          <label><span>Created Date:</span> <input type="date"
                                                   onChange={this.handleChange.bind(this, 'createDate')}/></label><br/>
          <button type="button" onClick={this.handleSubmit}>Upload</button>
        </form>
        <FileList ref="fileList"/>
      </div>
    );
  }
}

export default App;
