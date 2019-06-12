#include<stdio.h>
#include<stdlib.h>
#include<string.h>

struct files{
    char *name;
    char *content;
    int num;
};

typedef struct files FileType;

int is_equal(char *one, char *two){
    int equal = 1;

    if (strlen(one) == strlen(two)) {
        int i;
        for(i = 0; i < strlen(one); i++){
            if (one[i] != two[i]) {
                equal = 0;
                break;
            }
        }
    }else{
        equal = 0;
    }
    return equal;   
}

int count_word(char *content, char *searched_word){
    char *contentcpy = malloc((strlen(content)+1)*sizeof(char));
    strcpy(contentcpy, content);
    char *word_of_content;
    word_of_content = strtok(contentcpy, " ");
    int counter = 0;
    while(word_of_content != NULL){
        int length_of_woc = strlen(word_of_content);
        int length_of_sw = strlen(searched_word);
        if (is_equal(word_of_content, searched_word)) {
            counter++;
        }else if ( length_of_woc > length_of_sw  ){
            int contains = 0;
            int i;
            for( i = 0; i < length_of_woc; i++){
                int j = 0;
                if (word_of_content[i] == searched_word[j]) {
                    for( j = 0; j < length_of_sw; j++)
                    {
                        if (word_of_content[i+j] == searched_word[j]){
                            contains = 1;
                        }else
                        {
                            contains = 0;
                            break;
                        }
                    }
                }
            }
            if (contains == 1) {
                counter++;
            }
        }
    
        word_of_content = strtok(word_of_content+strlen(word_of_content)+1," ");

    }
    return counter;

}

int count_sentences(char *line){
    int i, sentence_num = 0;
    for( i = 0; i < strlen(line); i++)
    {
        if (line[i] == '.' || line[i] == '?' || line[i] == '!') {
            sentence_num++;
        }
    }
    return sentence_num;
    
}

int in_array(int *array, int item, int length, char *string, char char_item, int array_or_string){
    if (array_or_string == 1) {
        int i;
        int rtn = 0;
        for( i = 0; i < length; i++)
        {
            if (array[i] == item) {
                rtn = 1;
                break;
            }
        }
        return rtn;
    }
    else
    {
        int i;
        int rtn = 0;
        for( i = 0; i < length; i++)
        {
            if (string[i] == char_item) {
                rtn = 1;
                break;
            }
        }
        return rtn;
    }
    
    

    
}

void print_to_output(char *line){
    FILE *outfile = fopen("output.txt", "a");
    if (line[strlen(line)-1] == '\n') {
        fprintf(outfile, "%s", line);    

    }else{
        fprintf(outfile, "%s\n", line);
    }    
    fclose(outfile);
}

int search_file(FileType **file_repository, char *filename, int *created_file_num){
    int found = -1;
    int i;
    for(i = 0; i < *created_file_num; i++){
        if (is_equal((*file_repository)[i].name, filename)) {
            found = 1;
            return i;
        }
    }
    if (found == -1) {
        return -1;
    }
    
}

int create_file(FileType ***file_repository, char *line, int *created_file_num){
    print_to_output(line);

    char *linecopy = malloc((strlen(line)+1)*sizeof(char));
    strcpy(linecopy, line);
    char *word = strtok(linecopy, " ");
    char *file_name;
    char *file_content = NULL;
    word = strtok(word+strlen(word)+1, " ");
        
    if ( is_equal(word, "-n") ) {
        word = strtok(word+strlen(word)+1, " ");
        file_name = malloc((strlen(word)+1)*sizeof(char));
        strcpy(file_name, word);

        char *namecpy = malloc((strlen(file_name)+1)*sizeof(char));
        strcpy(namecpy, file_name);
        if (in_array(NULL, 0, strlen(namecpy), namecpy, '.', 0)) {
            char *extension = strtok(namecpy, ".");
            extension = strtok(NULL, ".");
            if (extension == NULL) {
                print_to_output("invalid command (there is no extension)");
                return 0;
            }
        }else
        {
            print_to_output("invalid command (there is no extension)");
            return 0;
        }

        if (search_file(*file_repository, file_name, created_file_num) != -1) {
            FILE *outfile = fopen("output.txt", "a");
            fprintf(outfile, "%s file has already been created\n", file_name);
            fclose(outfile);
            return 0;
        }
        word = strtok(word+strlen(word)+1, " ");
        if (strlen(word) < 4) {
            if ( is_equal(word, "-t") ) {
                file_content = malloc(sizeof(char)*(strlen(word+strlen(word)+1)+1));
                strcpy(file_content, word+strlen(word)+1);
                if (strlen(file_content) == 0) {
                    file_content = "Empty File";
                }
            }
            else if (word[0] == '-' && word[1] == 't' && word[2] == '\n'){
                file_content = "Empty File";
            }
            else{
                print_to_output("invalid command in create file");
                return 0;
            }            
        }
        else{
            print_to_output("invalid command (create file)");
            return 0;
        }
    }
    else if ( is_equal(word, "-t") ){
        word = strtok(word+strlen(word)+1, " ");
        if (is_equal(word, "-n")) {
            file_content = "Empty File";
        }else
        {
            while(!is_equal(word, "-n") && word != NULL){
                if (file_content == NULL) {
                    file_content = malloc(sizeof(char)*(strlen(word)+1));
                    
                    strcpy(file_content, word);
                    strcat(file_content, " ");
                    word = strtok(word+strlen(word)+1, " ");
                    continue;
                }
                
                file_content = realloc(file_content, sizeof(char)*(strlen(file_content)+strlen(word)+2));
                strcat(file_content, word);
                strcat(file_content, " ");
                word = strtok(word+strlen(word)+1, " ");
            }
            if (is_equal(word, "-n")) {
                word = strtok(word+strlen(word)+1, " ");
                file_name = malloc((strlen(word)+1)*sizeof(char));
                strcpy(file_name, word);
                char *namecpy = malloc((strlen(file_name)+1)*sizeof(char));
                strcpy(namecpy, file_name);
                if (in_array(NULL, 0, strlen(namecpy), namecpy, '.', 0)) {
                    char *extension = strtok(namecpy, ".");
                    extension = strtok(NULL, ".");
                    if (extension == NULL) {
                        print_to_output("invalid command (there is no extension)");
                        return 0;
                    }
                }else
                {
                    print_to_output("invalid command (there is no extension)");
                    return 0;
                }
                if (search_file(*file_repository, file_name, created_file_num) != -1) {
                    FILE *outfile = fopen("output.txt", "a");
                    fprintf(outfile, "%s file has already been created\n", file_name);
                    fclose(outfile);
                    return 0;
                }
                
            }else{
                print_to_output("there is no -n");
            }
        }
    }else
    {
        print_to_output("invalid command (create file)");
        return 0;
    }

    
    
    **file_repository = realloc(**file_repository, sizeof(FileType) * ((*created_file_num)+1));
    (**file_repository)[*created_file_num].name = malloc((strlen(file_name)+1)*sizeof(char));
    strcpy((**file_repository)[*created_file_num].name, file_name);
    if (file_content[strlen(file_content)-1] == '\n') {
        (**file_repository)[*created_file_num].content = malloc((strlen(file_content))*sizeof(char));
        strncpy((**file_repository)[*created_file_num].content, file_content, strlen(file_content)-1);
    }else
    {
        (**file_repository)[*created_file_num].content = malloc((strlen(file_content)+1)*sizeof(char));
        strcpy((**file_repository)[*created_file_num].content, file_content);

    }
    (*created_file_num)++;
    return 0;
}

int delete_file(FileType **file_repository,char *line, int *created_file_num, int** deleted_files, int *delete_num){
    
    char *linecopy = malloc((strlen(line)+1)*sizeof(char));
    strcpy(linecopy,line);
    char *word;

    word = strtok(linecopy, " ");
    word = strtok(NULL, " ");
    int returned_value;
    if (is_equal(word, "-n")){
        word = strtok(NULL, " \n");
        returned_value = search_file(file_repository, word, created_file_num);
        
        if (returned_value == -1) {
            FILE *outfile = fopen("output.txt", "a");
            fprintf(outfile,"there is no file named %s\n", word);
            fclose(outfile);
            return 0;
        }else
        {

            void *temp = &((*file_repository)[returned_value]);
            free((*file_repository)[returned_value].content);
            free((*file_repository)[returned_value].name);
            (*deleted_files) = realloc(*deleted_files, sizeof(int)*((*created_file_num)+1));
            (*deleted_files)[*delete_num] = returned_value;
            (*delete_num)++;
        }
    }
    print_to_output(line);
    return 0;
}

void print_command(FileType **file_repository, char *line, int *created_file_num, int **deleted_files, int *delete_num){
    print_to_output(line);
    FILE *outfile;
    char *linecpy = malloc(sizeof(char)*(strlen(line)));
    strcpy(linecpy, line);
    char *word = strtok(linecpy, " ");
    word = strtok(NULL, " ");

    while(word != NULL && !is_equal(word, "\n")){

        if (is_equal(word, "-a") || is_equal(word, "-a\n")) {
            int i;
            for( i = 0; i < *created_file_num; i++){
                outfile = fopen("output.txt", "a");
                fprintf(outfile, "Filename %d:\t%s\n", i+1, (*file_repository)[i].name);
                fclose(outfile);                    
            }
        }


        else if (is_equal(word, "-e"))
        {
            word = strtok(word+strlen(word)+1, " ");
            char *extention;

            extention = malloc((strlen(word)+1)*sizeof(char));
            strcpy(extention, word);
            char *file_name;
            int i;
            for( i = 0; i < *created_file_num; i++)
            {
                file_name = malloc((strlen((*file_repository)[i].name)+1)*sizeof(char));
                strcpy(file_name, (*file_repository)[i].name);
                file_name = strtok(file_name, ".");
                if ( is_equal(file_name+strlen(file_name)+1, extention)) {
                    outfile = fopen("output.txt", "a");
                    fprintf(outfile, "Filename %d: %s\nText: %s", i+1, file_name, (*file_repository)[i].content);
                    if ((*file_repository)[i].content[strlen((*file_repository)[i].content)-1] != '\n') {
                        fprintf(outfile, "\n");
                    }
                    
                    fclose(outfile);
                }
                
            }
                

        }
        else if (is_equal(word, "-c") || is_equal(word, "-c\n"))
        {
            outfile = fopen("output.txt", "a");
            int i;
            for( i = 0; i < *created_file_num; i++)
            {
                if (!in_array(*deleted_files, i, *delete_num, NULL, '0', 1)) {
                    fprintf(outfile, "Num:\t%d\nName:\t%s\nText:\t%s\n", i+1, (*file_repository)[i].name, (*file_repository)[i].content);
                }
            }
            fclose(outfile);
        }
        else if (is_equal(word, "-n"))
        {
            char *file_name;
            word = strtok(word+strlen(word)+1, " ");
            file_name = malloc((strlen(word)+1)*sizeof(char));
            strcpy(file_name, word);
            int returned_value = search_file(file_repository, file_name, created_file_num);
            
            if (returned_value != -1 ) {
                word = strtok(word+strlen(word)+1, " ");  
                    
                if (is_equal(word, "-t") || (word[0] == '-' && word[1] == 't' && word[2] == '\n') ) {
                    outfile=fopen("output.txt", "a");
                    fprintf(outfile, "Text:\t%s\n", (*file_repository)[returned_value].content);
                    fclose(outfile);
                }
                else if (is_equal(word, "-cw"))
                {
                    word = strtok(word+strlen(word)+1, " ");    
                    char *searched_word = malloc((strlen(word)+1)*sizeof(char));
                    strcpy(searched_word, word);
                    char *content = malloc((strlen((*file_repository)[returned_value].content)+1)*sizeof(char));
                    strcpy(content, (*file_repository)[returned_value].content);
                    int num_of_word = count_word(content, searched_word);
                    outfile=fopen("output.txt", "a");
                    fprintf(outfile, "Text:\t%s\nNumber Of Occurrence of \"%s\": %d\n", (*file_repository)[returned_value].content, searched_word, num_of_word);
                    fclose(outfile);
                }else if ( is_equal(word, "-cs") || (word[0] == '-' && word[1] == 'c' && word[2] == 's' && word[3] == '\n') )
                {
                    int sentence_num = count_sentences((*file_repository)[returned_value].content);
                    outfile =fopen("output.txt", "a");
                    fprintf(outfile, "Text: %s\nNumber Of Sentences : %d\n", (*file_repository)[returned_value].content, sentence_num);
                    fclose(outfile);
                }
                
            }
        }
        word = strtok(word+strlen(word)+1, " ");    
    }
}

int remove_text(FileType ***file_repository, char *line, int *created_file_num ){
    print_to_output(line);
    char *linecopy = malloc((strlen(line)+1)*sizeof(char));
    strcpy(linecopy, line);
    char *word = strtok(linecopy, " ");
    int starting_index, length;
    word = strtok(NULL, " ");

    if (!is_equal(word, "-n")) {
        print_to_output("invalid command (remove text)");
        return 0;
    }
    word = strtok(NULL, " ");
    int returned_value;
    returned_value = search_file(*file_repository, word, created_file_num);
    
    if (returned_value == -1) {
        FILE *outfile = fopen("output.txt", "a");
        fprintf(outfile, "There is no file named %s\n", word);
        fclose(outfile);
        return 0;
    }
    else
    {
        word = strtok(NULL, " "); 
        if (is_equal(word, "-s")) {
            word = strtok(NULL, " ");
            starting_index = atoi(word);
        }else
        {
            print_to_output("invalid command (-s remove text)");
            return 0;
        }
        word = strtok(NULL, " ");
        if (is_equal(word, "-l")) {
            word = strtok(NULL, " ");
            length = atoi(word);
        }else
        {
            print_to_output("invalid command (-l remove text)");
            return 0;
        }
        if (starting_index+length > strlen((**file_repository)[returned_value].content)) {
            print_to_output("invalid size");
            return 0;
        }
        if (starting_index < 0 || starting_index > strlen((**file_repository)[returned_value].content) || length > strlen((**file_repository)[returned_value].content)-starting_index) {
            print_to_output("Invalid size!");
            return 0;
        }
        
        char *content = malloc(sizeof(char)*(strlen((**file_repository)[returned_value].content)-length+1));
        int initial_length = strlen((**file_repository)[returned_value].content);
        int i;
        for(i = 0; i < (initial_length - length ); i++){
            if (i >= starting_index) {
                content[i] = (**file_repository)[returned_value].content[i+length];
            }else{
                content[i] = (**file_repository)[returned_value].content[i];
            }
        }
        (**file_repository)[returned_value].content = realloc((**file_repository)[returned_value].content, strlen(content));
        
        strcpy((**file_repository)[returned_value].content, content);
    }
    return 0;
}

int append_text(FileType ***file_repository, char *line, int *created_file_num){
    print_to_output(line);
    char *linecpy = malloc((strlen(line)+1)*sizeof(char));
    strcpy(linecpy, line);
    char *word = strtok(linecpy, " ");


    char *file_name;
    char *appending_content = malloc(sizeof(char*));


    word = strtok(word+strlen(word)+1, " ");

    if ( is_equal(word, "-n") ) {
        word = strtok(word+strlen(word)+1, " ");
        file_name = malloc((strlen(word)+1)*sizeof(char));
        strcpy(file_name, word);
        word = strtok(word+strlen(word)+1, " ");
        if ( is_equal(word, "-t") ) {
            appending_content = malloc(sizeof(char)*(strlen(word+strlen(word)+1)));
            strncpy(appending_content, word+strlen(word)+1, strlen(word+strlen(word)+1));
        }
        else
        {
            print_to_output("invalid command (append text)");
            return 0;
        }
    }
    
    else if ( is_equal(word, "-t") ){
        word = strtok(word+strlen(word)+1, " ");
        while(!is_equal(word, "-n") && word != NULL){
            appending_content = realloc(appending_content ,strlen(word)+sizeof(char));
            strcat(appending_content, word);
            strcat(appending_content, " ");
            word = strtok(word+strlen(word)+1, " ");
        }
        if (is_equal(word, "-n")) {
            word = strtok(word+strlen(word)+1, " ");
            file_name = malloc(sizeof(char)*(strlen(word)+1));
            strcpy(file_name, word);
        }else{
            print_to_output("invalid command (append text)");
            return 0;
        }
    }
    int returned_value = search_file(*file_repository, file_name, created_file_num);
    if (returned_value == -1) {
        FILE *outfile = fopen("output.txt", "a");
        fprintf(outfile, "There is no file named %s\n", file_name);
        fclose(outfile);
        return 0;
    }else
    {
        int new_size = strlen(appending_content) + strlen((**file_repository)[returned_value].content);
        char *new_content = malloc((new_size+1)*sizeof(char));
        strcpy(new_content, (**file_repository)[returned_value].content);
        strcat(new_content, appending_content);
        (**file_repository)[returned_value].content = malloc((strlen(new_content)+1)*sizeof(char));
        strcat((**file_repository)[returned_value].content, new_content);
    }
    return 0;
}

int replace_text(FileType ***file_repository, char *line, int *created_file_num){
    print_to_output(line);
    char *linecpy = malloc((strlen(line)+1)*sizeof(char));
    strcpy(linecpy, line);
    char *word = strtok(linecpy, " ");
    word = strtok(word+strlen(word)+1, " ");
    char *old_word, *new_word, *file_name;
    int check_commands = 0;
    int c = 0;
    while(word != NULL && !is_equal(word, "\n") && (c<3)){
        if (is_equal(word, "-n")) {
            word = strtok(word+strlen(word)+1, " ");
            file_name = malloc((strlen(word)+1)*sizeof(char));
            strcpy(file_name, word);
            check_commands++;
        }else if (is_equal(word, "-ow"))
        {
            word = strtok(word+strlen(word)+1, " ");
            old_word = malloc((strlen(word)+1)*sizeof(char));
            strcpy(old_word, word);
            check_commands++;
        }else if (is_equal(word, "-nw"))
        {
            word = strtok(word+strlen(word)+1, " ");
            new_word = malloc((strlen(word)+1)*sizeof(char));
            strcpy(new_word, word);
            check_commands++;
        }
        else
        {
            print_to_output("invalid command1 (replace)");
            return 0;
        }
        word = strtok(word+strlen(word)+1, " ");
        c++;
    }
    if (check_commands != 3) {
        print_to_output("Invalid command2 (replace)");
        return 0;
    }
    
    int returned_value = search_file(*file_repository, file_name, created_file_num);
    
    if (returned_value == -1) {
        print_to_output("Invalid command3 (replace)");
        return 0;
    }
    
    char *content = malloc((strlen((**file_repository)[returned_value].content)+1)*sizeof(char));
    strcpy(content, (**file_repository)[returned_value].content);
    char *newcontent;
    int differance = strlen(old_word) - (strlen(new_word)-1);
    if ( differance < 0 ) {
        newcontent = malloc(strlen(((**file_repository)[returned_value].content)+strlen(new_word)+1)*sizeof(char));
    }else
    {
        
        newcontent = malloc((strlen((**file_repository)[returned_value].content)+1)*sizeof(char));
        
    }

    char *word_of_content;
    

    int starting_index = 0, contains = 0;
    int starting_indexes[strlen(content)];
    int num_of_matches = 0;
    int i;
    for( i = 0; i < strlen(content); i++)
    {
        int j = 0;
        if (content[i] == old_word[j]) {
            starting_index = i;
            for( j = 0; j < strlen(old_word); j++)
            {
                if (content[i+j] == old_word[j]) {
                    contains = 1;
                }else{
                    contains = 0;
                    break;
                }
                
            }
            
            if (contains == 1) {
                starting_indexes[num_of_matches] = starting_index;
                contains = 0;
                num_of_matches++;
                i = i+j;
            }
            
        }
        
    }
    int u;
    for( u = 0; u < starting_indexes[0]; u++){
    newcontent[u] = content[u];
    }
    int x;
    for( x = 0; x < num_of_matches; x++)
    {
        u = 0;

        
        for( u = 0; u < strlen(new_word); u++)
        {
           
            newcontent[starting_indexes[x]+u-(differance*x)] = new_word[u];    
        }
        u = 0;
        for(u = 0; u < (strlen(content)-starting_indexes[x]); u++)
        {
            newcontent[starting_indexes[x]+strlen(new_word)+u-(differance*x)] = content[starting_indexes[x]+strlen(old_word)+u];
        }
    }
    (**file_repository)[returned_value].content = realloc((**file_repository)[returned_value].content, sizeof(char)*(strlen(newcontent)+1));
    strcpy((**file_repository)[returned_value].content, newcontent);

    return 0;
}

int read_lines(char *inputname, char ***lines){
    FILE *fileptr;
    if( ( fileptr = fopen(inputname, "r" ) ) == NULL ){
        printf( "File could not be opened\n" );}
    char letter;
    int line_num = 0, line_size = 0, max_line = 0;
    
    while(!feof(fileptr)){
        fscanf(fileptr, "%c", &letter);
        line_size++;
        if (letter == '\n'){
            *lines = (char**) realloc(*lines,((line_num + 1)*sizeof(char*)));
            *(*lines + line_num) = malloc((line_size)*sizeof(char));
            line_num++;
            if(line_size > max_line){
                max_line = line_size;
            }
            line_size = 0;
        }
    }
    char *temp_line = malloc(sizeof(char)*(max_line+1));
    rewind(fileptr);
    int i;
    for(i = 0; i<line_num; i++){
        fgets(temp_line, max_line+1, fileptr);
        int letter = 0;
        for( letter = 0; letter < strlen(temp_line); letter++)
        {
            if (temp_line[letter] != '\n') {
                (*lines)[i][letter] = temp_line[letter];
            }
        }

    }

    if( ( fileptr = fopen(inputname, "r" ) ) != NULL ){
        fclose(fileptr);}
    return line_num-1;
}

void execute_commands(char **lines, int line_num, FileType **file_repository, int *created_file_num, int **deleted_files, int *delete_num){
    char *command;
    char *one_line =(char *) malloc(0);
    char *linecpy =(char *) malloc(0);
    

    int i;
    for (i = 0; i<line_num; i++){
        one_line = (char *) realloc(one_line, (strlen(lines[i]) * sizeof(char*))+1);
        strcpy(one_line, lines[i]);
        command = strtok(one_line, " ");
        
        if (is_equal(command, "create") == 1){
            create_file(&file_repository, lines[i], created_file_num);
            }
        else if (is_equal(command, "print") == 1){
            print_command(file_repository, lines[i], created_file_num, deleted_files, delete_num);
            }
        else if (is_equal(command, "remove") == 1){
            remove_text(&file_repository, lines[i], created_file_num);
            }
        else if (is_equal(command, "delete") == 1){
            delete_file(file_repository, lines[i], created_file_num, deleted_files, delete_num);
            }
        else if (is_equal(command, "append") == 1){
            append_text(&file_repository, lines[i], created_file_num);
            }
        else if (is_equal(command, "replace") == 1){
            replace_text(&file_repository, lines[i], created_file_num);
            }
        else{
            print_to_output(lines[i]);
            print_to_output("Invalid command!");}   
    }


}

int main(int argc, char *argv[]){
    int x = 0;    
    FileType *file_repository = malloc(sizeof(FileType));
    int created_file_num = 0;
    int *deleted_files = malloc(0);
    int delete_num = 0;
    char *inputname = malloc(sizeof(char)*(strlen(argv[1])+1));
    strcpy(inputname, argv[1]);
    char **lines =  malloc(sizeof(char*));
    int line_num;
    line_num = read_lines(inputname, &lines);
    execute_commands(lines, line_num, &file_repository, &created_file_num, &deleted_files, &delete_num);
    return 0;
}
