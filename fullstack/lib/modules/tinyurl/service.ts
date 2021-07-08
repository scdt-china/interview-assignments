import { ITinyUrl } from './model';
import tinyUrl from './schema';

export default class TinyUrlService {
    
    public createUser(tiny_url_params: ITinyUrl, callback: any) {
        const _session = new tinyUrl(tiny_url_params);
        _session.save(callback);
    }

    public filterUser(query: any, callback: any) {
        tinyUrl.findOne(query, callback);
    }

    public updateUser(tiny_url_params: ITinyUrl, callback: any) {
        const query = { _id: tiny_url_params._id };
        tinyUrl.findOneAndUpdate(query, tiny_url_params, callback);
    }
    
    public deleteUser(_id: String, callback: any) {
        const query = { _id: _id };
        tinyUrl.deleteOne(query, callback);
    }

}