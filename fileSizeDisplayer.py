import os

def countLine(filePath):
    count = 0
    file = open(filePath, encoding="utf8")
    content = file.read()
    file.close()
    listOfLines = content.split("\n")
    count = len(listOfLines)
    return count

fileConsidered = ["C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\company_name.csv",
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\comp_cast_type.csv",
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\info_type.csv",
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\keyword.csv",
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\kind_type.csv",
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\link_type.csv",
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\movie_link.csv",
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\role_type.csv",
                "C:\\Users\\lenge\\IdeaProjects\\INFO-H417-group4\\imdb\\cast_info.csv"]

numLines = [countLine(x) for x in fileConsidered]
fileSize = [os.stat(x).st_size for x in fileConsidered]
fileOrdered = sorted(range(len(fileSize)), key = lambda k : fileSize[k])
sizeOrdered = sorted(fileSize)
fileSorted = [fileConsidered[x] for x in fileOrdered]
numLinesSorted = [numLines[x] for x in fileOrdered]

