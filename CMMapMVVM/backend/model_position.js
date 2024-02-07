const Sequelize = require("sequelize");
const sequelize = require("./db_instance");

const position = sequelize.define(
    "position",
    {
      // attributes
      latitude: {
        type: Sequelize.STRING,
        allowNull: false
      },
      longitude: {
        type: Sequelize.STRING,
        allowNull: false
      }
    },
    {
      // options
    }
  );
  

(async () => {
  await position.sync({ force: false });    
})();

module.exports = position;
