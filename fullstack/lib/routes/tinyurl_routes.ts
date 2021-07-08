//lib/routes/tinyurl_routes.ts
import {Application, Request, Response } from 'express';
import { TinyUrlController } from '../controllers/tinyUrlController';

export class TinyUrlRoutes {

   private tiny_url_controller: TinyUrlController = new TinyUrlController();

   public route(app: Application) {

      app.get('/api/tiny_url/:tiny_url', (req: Request, res: Response) => {
         this.tiny_url_controller.get_tiny_url(req, res);
      });

      app.post('/api/tiny_url', (req: Request, res: Response) => {
         this.tiny_url_controller.create_tiny_url(req, res);
      });
      
   }
}