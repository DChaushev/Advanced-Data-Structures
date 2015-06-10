/* 
 * File:   FileReader.hpp
 * Author: Dimitar
 *
 * Created on June 10, 2015, 12:37 PM
 */

#ifndef FILEREADER_HPP
#define	FILEREADER_HPP
#include <vector>
#include <string>

class FileReader {
public:
    static std::vector<std::string> getLines(std::string file);
};

#endif	/* FILEREADER_HPP */

