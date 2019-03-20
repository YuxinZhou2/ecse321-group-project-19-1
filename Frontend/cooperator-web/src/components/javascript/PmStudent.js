import axios from "axios";
import { log } from "util";
var config = require("../../../config");

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'https://cooperator-backend-260.herokuapp.com/'


var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  data() {
    return {
      students: [],

      fields: {
        firstName: {
          label: "FirstName",
          sortable: true
        },
        lastName: {
          label: "LastName",
          sortable: true
        },
        studentId: {
          label: "ID",
          sortable: true
        },
        problematic: {
          label: "Problematic Status",
          sortable: true
        },
        coopStatus: {
          label: "Coop Status",
          sortable:true
        }
      }
    };
  },

  created: function() {
    
    // Initializing people from backend
    AXIOS.get(`/allStudents`)
      .then(response => {
        this.students = response.data;
      })
      .then(() => {
        for (var i = 0; i < this.students.length; i++) {
          var student = this.students[i];
          if (student.coopPositions.length > 0) {
            student.coopStatus = student.coopPositions[0].status;
          } else student.coopStatus = null;
        }    
            
        this.$refs.table.refresh();
      })
      .catch(error => {
        alert(error);
      });    
  }
};
