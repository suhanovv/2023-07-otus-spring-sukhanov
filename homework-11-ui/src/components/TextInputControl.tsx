import React from "react";

export default function TextInputControl({fieldName, fieldLabel, defaultValue, errorMsg, onChange}: {fieldName: string, fieldLabel: string, defaultValue: string, errorMsg: string, onChange: any}) {
    return (
        <div className="row">
            <label htmlFor={fieldName + "-input"}>{fieldLabel} :</label>
            <input type="text" name={fieldName} defaultValue={defaultValue} onChange={onChange}/>
            {errorMsg !== "" ? <div className="errors" >{errorMsg}</div> : null }
        </div>
    )
}