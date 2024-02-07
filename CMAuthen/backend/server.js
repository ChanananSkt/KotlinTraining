var express = require('express');
var formidable = require('formidable');
var path = require('path');
var app = express();
var fs = require("fs-extra");
app.use(express.static('uploads'));

app.get("/", (req, res)=>{
    res.end("Home");
})

app.get("/upload", (req, res)=>{
    res.json({result:"ok", detail: req.query});
})



app.post('/uploads/', function (req, res) {

    try {
        var form = new formidable.IncomingForm();
        var date = Date.now();

        form.parse(req, function (err, fields, files) {

            console.log(JSON.stringify(files));

            var oldpath = files.userfile.path;
            var newpath = path.join(__dirname, "./uploads/" + date.toString() + "_" + files.userfile.name);

            fs.move(oldpath, newpath, function (err) {
                if (err) throw err;

              var username = fields.username;
              var password = fields.password;

              console.log("username: " + username);
              console.log("password: " + password);

              res.end("Upload Successfully\n" + username + " " + password);
            });
        });
    } catch (err) {
        console.log("err : " + err);
    }
});



app.listen(3000,()=>{
    console.log("Running.");
})


// nodemon server.js