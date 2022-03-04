#ifndef RC_H
#define RC_H

enum ReturnCode {
    OK, ERR_OPEN_FILE, ERR_EMPTY, ERR_INPUT, ERR_MEMORY, ERR_PARAMETR
};

void print_warning(ReturnCode rc);

#endif // RC_H

