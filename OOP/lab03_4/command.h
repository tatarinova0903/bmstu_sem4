#ifndef COMMAND_H
#define COMMAND_H

#include <string>

class base_command
{
public:
    base_command() = default;
    virtual ~base_command() = default;
    virtual void execute() = 0;
};

#endif
