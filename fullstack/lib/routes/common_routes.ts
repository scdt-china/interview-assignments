//lib/routes/common_routes.ts
import { Application, Request, Response } from 'express';

export class CommonRoutes {
   public route(app: Application) {
      // Mismatch URL
      app.all('*', function (req: Request, res: Response) {
         res.status(404).send({ error: true, message: 'Check your URL please' });
      });
   }
}