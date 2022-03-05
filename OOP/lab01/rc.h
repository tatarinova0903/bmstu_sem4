#ifndef RC_H
#define RC_H

enum return_code {
    OK, ERR_OPEN_FILE, ERR_EMPTY, ERR_INPUT, ERR_MEMORY, ERR_PARAMETR
};

void print_warning(return_code rc);

#endif // RC_H

