
///////////////////
//    Globals    //
///////////////////

var recordedVariableNames = new Set(); // Already used variable names
var varCount = 1; // Used to set unique IDs for variables
var predCount = 1; // Used to set unique IDs for predicates

////////////////////////////////////////////
//    Define Variables Table Functions    //
////////////////////////////////////////////

// Adds a new variable to the table of defined variables
function addVarRow() {

    var userInputtedVariableName = document.getElementById('defined_variable_1').value;
    var userInputtedVariableValue = document.getElementById('defined_value_1').value;

    // Check if variable name already exists
    if(recordedVariableNames.has(userInputtedVariableName)) {
        console.log(userInputtedVariableName);
        document.getElementById("varErrorMessage").innerHTML = 'Variable name "' + userInputtedVariableName + '" already exists. Please enter a unique variable name.';
        return;
    } else {
        recordedVariableNames.add(userInputtedVariableName);
        document.getElementById("varErrorMessage").innerHTML = ''

        varCount++;
        var oRow = document.getElementById("var_table").insertRow(document.getElementById("var_table").rows.length);
        oRow.onmouseover = function () { document.getElementById("var_table").clickedRowIndex = this.rowIndex; };
        var oCell1 = oRow.insertCell(0);
        var oCell2 = oRow.insertCell(1);
        var oCell3 = oRow.insertCell(2);
        var oCell4 = oRow.insertCell(3);

        var curRowIndex = document.getElementById("var_table").rows.length - 1;

        oCell1.innerHTML = "<p id='defined_variable_" + varCount + "' >" + userInputtedVariableName + "</p>";
        oCell2.innerHTML = "=";
        oCell3.innerHTML = "<p id='defined_value_" + varCount + "' >" + userInputtedVariableValue + "</p>";
        oCell4.innerHTML = "<th><button type=button class='fa fa-trash button' style='font-size:24px' onClick='delVarRow()'></button></th>"



        // Go through each logical predicate row and append to boolean variable options
        var logicTable = document.getElementById('logic_table');
        var logicRows = logicTable.rows;
        for(var r = 1, n = logicRows.length; r < n; r++) {

            var curLogicRow = logicRows[r];
            console.log(curLogicRow.cells[0]);

            // Extract current logical predicate row's ID
            var curRow = curLogicRow.cells[0].innerHTML;
            var curRowID = curRow.match('["].\+["]')[0];
            var curRowIDNum = curRowID.match('[_][0-9]+')[0].substring(1);

            console.log('ops1_' + curRowIDNum);

            var boolvar1 = document.getElementById('ops1_' + curRowIDNum);
            var boolvar2 = document.getElementById('ops2_' + curRowIDNum);

            var newOption1 = document.createElement("option");
            newOption1.text = document.getElementById("defined_variable_1").value;
            newOption1.value = document.getElementById('defined_value_1').value;

            var newOption2 = document.createElement("option");
            newOption2.text = document.getElementById("defined_variable_1").value;
            newOption2.value = document.getElementById('defined_value_1').value;

            boolvar1.add(newOption1);
            boolvar2.add(newOption2);

        }

        document.getElementById("defined_variable_1").value = "";
    }


}

// Deletes defined variable
function delVarRow() {

    // Delete from recoreded variable names
    var selectedIndex = document.getElementById("var_table").clickedRowIndex;
    var selectedVariable = document.getElementById("var_table").rows[selectedIndex].cells[0].innerHTML;
    var extractedText = selectedVariable.match("[>].\+[<]")[0];
    extractedText = extractedText.substring(1, extractedText.length-1);
    recordedVariableNames.delete(extractedText);

    // Delete from list of variables
    document.getElementById("var_table").deleteRow(selectedIndex);

    // Delete from options drop down in predicates table
    var logicTable = document.getElementById('logic_table');
    var logicRows = logicTable.rows;
    for (var r = 1, n = logicRows.length; r < n; r++) {

        // Extract current variable row's ID
        var curRow = logicRows[r].cells[0].innerHTML;
        var curRowID = curRow.match('["].\+["]')[0];
        var curRowIDNum = curRowID.match('[_][0-9]+')[0].substring(1);

        var boolvar1 = document.getElementById('ops1_' + curRowIDNum);
        var boolvar2 = document.getElementById('ops2_' + curRowIDNum);
        boolvar1.remove(document.getElementById("var_table").clickedRowIndex);
        boolvar2.remove(document.getElementById("var_table").clickedRowIndex);
    }
}

//////////////////////////////////////////////
//    Logical Predicates Table Functions    //
//////////////////////////////////////////////

// Adds a new logical predicate statement to the predicate table
function addPredRow() {
    predCount++;
    var oRow = document.getElementById("logic_table").insertRow(document.getElementById("logic_table").rows.length);
    oRow.onmouseover = function () { document.getElementById("logic_table").clickedRowIndex = this.rowIndex; };
    var oCell1 = oRow.insertCell(0);
    var oCell2 = oRow.insertCell(1);
    var oCell3 = oRow.insertCell(2);
    var oCell4 = oRow.insertCell(3);
    var oCell5 = oRow.insertCell(4);


    var curRowIndex = document.getElementById("logic_table").rows.length - 1;
    console.log(curRowIndex)

    // Go through each defined variable row and populate the boolean variable options

    var varTable = document.getElementById('var_table');

    var bool1Options = `<select type=text name=Boolean1_` + predCount + `[] id="ops1_` + predCount + `">`;
    var bool2Options = `<select type=text name=Boolean2_` + predCount + `[] id="ops2_` + predCount + `">`;

    var defaultOptions = `<option value="true">True</option><option value="false">False</option>`;
    bool1Options += defaultOptions;
    bool2Options += defaultOptions;


    // Instead of normal for loop, go through elements from DOM
    var varRows = varTable.rows
    for(var r = 2, n = varRows.length; r < n; r++) {

        // Extract current variable row's ID
        var curRow = varRows[r].cells[0].innerHTML;
        var curRowID = curRow.match('["].\+["]')[0];
        var curRowIDNum = curRowID.match('[_][0-9]+')[0].substring(1);

        console.log(curRowID);
        console.log(curRowIDNum);

        var curVariable = document.getElementById('defined_variable_' + curRowIDNum).innerHTML;
        var curValue = document.getElementById('defined_value_' + curRowIDNum).innerHTML;
        console.log(curVariable);
        bool1Options += `<option value="` + curValue + `">` + curVariable + `</option>`;
        bool2Options += `<option value="` + curValue + `">` + curVariable + `</option>`;

    }

    bool1Options += `</select>`;
    bool2Options += `</select>`;

    oCell1.innerHTML = bool1Options;

    oCell2.innerHTML = `
        <select type=text name=LogicOp1_` + predCount + `[] id=LogicOp1_` + predCount + `>                                       
            <optgroup label="Logical Operators">
                    <option value="logic-and">&&</option>
                    <option value="logic-or">||</option>
            </optgroup>
            <optgroup label="Bitwise Operators">
                    <option value="bitwise-and">&</option>
                    <option value="bitwise-or">|</option>
            </optgroup>
        </select>
    `;

    oCell3.innerHTML = bool2Options;

    oCell4.innerHTML = `
        <select type=text name=LogicOp2_` + predCount + `[] id=LogicOp2_` + predCount + `>
            <option value="none">None</option>

            <optgroup label="Logical Operators">
                    <option value="logic-and">&&</option>
                    <option value="logic-or">||</option>
            </optgroup>
            <optgroup label="Bitwise Operators">
                    <option value="bitwise-and">&</option>
                    <option value="bitwise-or">|</option>
            </optgroup>
        </select>
    `;

    oCell5.innerHTML = "<th><button type=button class='fa fa-trash button' style='font-size:24px;' onClick='delPredRow()'></button></th>";

    // Force the user to select a logical operator to link the next predicate
    if (curRowIndex > 1) {

        var table = document.getElementById('logic_table');
        var secondToLastRow = table.rows[curRowIndex - 1];

        // Remove None option while maintaining same id as before and remove
        secondToLastRow.cells[3].innerHTML = `
            ` + secondToLastRow.cells[3].innerHTML.split('\n')[1] + `
            <optgroup label="Logical Operators">
                    <option value="logic-and">&amp;&amp;</option>
                    <option value="logic-or">||</option>
            </optgroup>
            <optgroup label="Bitwise Operators">
                    <option value="bitwise-and">&amp;</option>
                    <option value="bitwise-or">|</option>
            </optgroup>
        </select>
                
        `
    }

}

// Deletes a logical predicate statement
function delPredRow() {

    var logicTable = document.getElementById("logic_table");
    let index = logicTable.clickedRowIndex;
    var logicRows = logicTable.rows;

    // When deleting the last logical predicate
    if (index == logicRows.length-1 && index != 1){
        console.log('deleting the last row');

        // Delete last row
        logicTable.deleteRow(document.getElementById("logic_table").clickedRowIndex);

        // Add and set None to logical operator 2 for the new last predicate using DOM manipulation
        logicRows[logicRows.length-1].cells[3].innerHTML = `
        ` + logicRows[logicRows.length-1].cells[3].innerHTML.split('\n')[1] + `
                <option value="none">None</option>
    
                <optgroup label="Logical Operators">
                        <option value="logic-and">&&</option>
                        <option value="logic-or">||</option>
                </optgroup>
                <optgroup label="Bitwise Operators">
                        <option value="bitwise-and">&</option>
                        <option value="bitwise-or">|</option>
                </optgroup>
            </select>
        `;
    } else {
        // Delete row
        logicTable.deleteRow(document.getElementById("logic_table").clickedRowIndex);
    }


}
