#ifndef BASE_CONTAINER_H
#define BASE_CONTAINER_H

#include <iostream>

namespace container {
    class base_container
    {
    public:
        explicit base_container(): len(0) {}
        virtual ~base_container(){}
        
        bool is_empty() const
        {
            return len == 0;
        }
        size_t size() const
        {
            return len;
        }
    protected:
        size_t len;
    };
}

#endif // BASE_CONTAINER_H
