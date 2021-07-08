import * as express from "express";
import * as bodyParser from "body-parser";
import * as mongoose from 'mongoose';
import environment from "../environment";
import { CommonRoutes } from "../routes/common_routes";
import { TinyUrlRoutes } from "../routes/tinyurl_routes";

class App {
   public app: express.Application;
   public mongoUrl: string = 'mongodb://localhost/' + environment.getDBName();
   private common_routes: CommonRoutes = new CommonRoutes();
   private tinyurl_routes: TinyUrlRoutes = new TinyUrlRoutes();

   constructor() {
      this.app = express();
      this.config();
      this.mongoSetup();
      this.tinyurl_routes.route(this.app);
      this.common_routes.route(this.app);
   }

   private config(): void {
      // support application/json type post data
      this.app.use(bodyParser.json());
      //support application/x-www-form-urlencoded post data
      this.app.use(bodyParser.urlencoded({ extended: false }));
   }

   private mongoSetup(): void {
      mongoose.connect(this.mongoUrl, {
         useNewUrlParser: true,
         useUnifiedTopology: true,
         useCreateIndex: true,
         useFindAndModify: false
      });
   }
}
export default new App().app;