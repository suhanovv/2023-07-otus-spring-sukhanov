import React from "react";

export default function SelectControl({fieldName, fieldLabel, defaultValue, data, optionText, onChange}: {fieldName: string, fieldLabel: string, defaultValue: string, data: any[], optionText: any, onChange: any}) {
    return(
        <div className="row">
            <label htmlFor={fieldName + '-input'}>{fieldLabel}: </label>
            <select name={fieldName} value={defaultValue} onChange={onChange}>
                {data.map(item => {
                    return (
                        <option key={fieldName + "-" + item.id} value={item.id}>{optionText(item)}</option>
                    )
                })}
            </select>
        </div>
    )
}