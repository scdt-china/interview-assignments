import { ModificationNote } from "../common/model";

export interface ITinyUrl {
    _id?: String;
    original_url: String,
    tiny_url: String,
    created_datetime: Date,
    is_deleted?: Boolean,
    modification_notes: ModificationNote[]
}